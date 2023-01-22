package monpackage;


import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.framework.OwnerPIN;

public class JavaCardServer extends Applet {

	/* Constants */
	public static final byte CLA_MONAPPLET = (byte) 0xB0;
	
	public static final byte INS_TEST_CODE_PIN = 0x00;
	public static final byte INS_INTERROGER_SOLDE= 0x01;
	public static final byte INS_INCREMENTER_SOLDE= 0x02;
	public static final byte INS_DECREMENTER_SOLDE = 0x03;

	public final static short MAX_SOLDE = 0x7FFF;// le maximum de la balance

	public final static byte MAX_MONTANT_TRANSACTION = (byte)127;// maximum montant
															// qu'on peut
															// transiter

	public final static byte MAX_ERROR_PIN = (byte) 0x03;// maximum de code pin
															// erroner

	public final static byte MAX_PIN_LENGTH = (byte) 0x04;// longeur maximale du
															// code pin

	
	private byte[] INIT_PIN = { (byte) 0, (byte) 0,(byte) 0,(byte) 0 };
	
	/* Exception */

	// Verification Pin Echoué
	final static short SW_VERIFICATION_FAILED = 0x6300;

	// signal the the PIN validation is required
	// for a credit or a debit transaction
	final static short SW_PIN_VERIFICATION_REQUIRED = 0x6301;

	// signal invalid transaction amount
	// amount > MAX_TRANSACTION_AMOUNT or amount < 0
	final static short SW_INVALID_TRANSACTION_AMOUNT = 0x6A83;

	// signal that the balance exceed the maximum
	final static short SW_EXCEED_MAXIMUM_SOLDE = 0x6A84;
	// signal the the balance becomes negative
	final static short SW_NEGATIVE_SOLDE = 0x6A85;


	/* variables */
	OwnerPIN pin;
	static short solde ;

	private JavaCardServer(byte bArray[], short bOffset, byte bLength) {
		pin = new OwnerPIN(MAX_ERROR_PIN, MAX_PIN_LENGTH);

		// Initialization parametre pin
		pin.update(INIT_PIN,(short) 0, (byte) 0x04);
	}

	public static void install(byte bArray[], short bOffset, byte bLength) throws ISOException {
		new JavaCardServer(bArray, bOffset, bLength).register();
	}

	public boolean select() {

		// pas de selection si le pin est blocker
		if (pin.getTriesRemaining() == 0)
			return false;

		return true;

	}

	public void deselect() {
		pin.reset();
	}

	public void process(APDU apdu) throws ISOException {

		// Buffer=Objet APDU porte un tableau tampon de byte qui transfére
		// l'entete + data entre la carte et le CAD
		// du APDU entrant et sortant

		byte[] buffer = apdu.getBuffer();

		// exception qui teste sur la commande de selection
		if (apdu.isISOInterindustryCLA()) {
			if (buffer[ISO7816.OFFSET_INS] == (byte) (0xA4)) {
				return;
			} else {
				ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
			}
		}

		// Vérifier si réinitialisation a une CLA correcte qui spécifie la
		// structure de commandement
		if (this.selectingApplet())
			return;
		if (buffer[ISO7816.OFFSET_CLA] != CLA_MONAPPLET) {
			ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
		}

		switch (buffer[ISO7816.OFFSET_INS]) {
		case INS_TEST_CODE_PIN:
			VerificationPIN(apdu);
			break;
		case INS_INCREMENTER_SOLDE:
			AjouterArgent(apdu);
			break;
		case INS_DECREMENTER_SOLDE:
			ExtraireArgent(apdu);
			break;
		case INS_INTERROGER_SOLDE:
			getBalance(apdu);
			break;

		default:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}

	}

	private void AjouterArgent(APDU apdu) {

		// access authentication
		if (!pin.isValidated())
			ISOException.throwIt(SW_PIN_VERIFICATION_REQUIRED);

		byte[] buffer = apdu.getBuffer();

		// Lc byte denotes the number of bytes in the
		// data field of the command APDU
		byte numBytes = buffer[ISO7816.OFFSET_LC];

		// indicate that this APDU has incoming data
		// and receive data starting from the offset
		// ISO7816.OFFSET_CDATA following the 5 header
		// bytes.
		byte byteRead = (byte) (apdu.setIncomingAndReceive());

		// it is an error if the number of data bytes
		// read does not match the number in Lc byte
		if ((numBytes != 1) || (byteRead != 1))
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);

		// get the credit amount
		byte creditAmount = buffer[ISO7816.OFFSET_CDATA];

		// check the credit amount
		if ((creditAmount > MAX_MONTANT_TRANSACTION) || (creditAmount < 0))
			ISOException.throwIt(SW_INVALID_TRANSACTION_AMOUNT);

		// check the new balance
		if ((short) (solde + creditAmount) > MAX_SOLDE)
			ISOException.throwIt(SW_EXCEED_MAXIMUM_SOLDE);

		// credit the amount
		solde = (short) (solde + creditAmount);

	}

	private void ExtraireArgent(APDU apdu) {

		// access authentication
		if (!pin.isValidated())
			ISOException.throwIt(SW_PIN_VERIFICATION_REQUIRED);

		byte[] buffer = apdu.getBuffer();

		byte numBytes = (byte) (buffer[ISO7816.OFFSET_LC]);

		byte byteRead = (byte) (apdu.setIncomingAndReceive());

		if ((numBytes != 1) || (byteRead != 1))
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);

		// get debit amount
		byte debitAmount = buffer[ISO7816.OFFSET_CDATA];

		// check debit amount
		if ((debitAmount > MAX_MONTANT_TRANSACTION) || (debitAmount < 0))
			ISOException.throwIt(SW_INVALID_TRANSACTION_AMOUNT);

		// check the new balance
		if ((short) (solde - debitAmount) < (short) 0)
			ISOException.throwIt(SW_NEGATIVE_SOLDE);

		solde = (short) (solde - debitAmount);

	}

	private void getBalance(APDU apdu) {

		byte[] buffer = apdu.getBuffer();


		short le = apdu.setOutgoing();

		if (le < 2)
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);

		apdu.setOutgoingLength((byte) 2);

		buffer[0] = (byte) (solde >> 8);
		buffer[1] = (byte) (solde & 0xFF);

		apdu.sendBytes((short) 0, (short) 2);

	}

	private void VerificationPIN(APDU apdu) {

		byte[] buffer = apdu.getBuffer();
		// retrieve the PIN data for validation.
		byte byteRead = (byte) (apdu.setIncomingAndReceive());

		// check pin
		// the PIN data is read into the APDU buffer
		// at the offset ISO7816.OFFSET_CDATA
		// the PIN data length = byteRead
		if (pin.check(buffer, ISO7816.OFFSET_CDATA, byteRead) == false)
			ISOException.throwIt(SW_VERIFICATION_FAILED);
	
	}
	
}