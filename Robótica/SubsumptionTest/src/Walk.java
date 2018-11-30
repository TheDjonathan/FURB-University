import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class Walk implements Behavior {

	/**
	 * Move o rob� para frente. Se a velocidade atual do motor A ou B for zero,
	 * Seta para velocidade padr�o e move para frente. 
	 */
	public void action() {
		System.out.println("FRENTE");
		Motor.A.forward();
        Motor.B.forward();
	}

	/**
	 * Para os motores A e B do rob� sem frear bruscamente.
	 */
	public void suppress() {
		Motor.A.stop();
		Motor.B.stop();
	}

	public boolean takeControl() {
		return true;
	}
}
