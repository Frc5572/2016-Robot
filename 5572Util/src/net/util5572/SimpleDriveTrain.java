package net.util5572;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class SimpleDriveTrain {

	private SpeedController[] left, right;
	private Joystick[] sticks;
	private boolean invLR = false, invFB = false;

	public final int controllerCount;

	/** Speedcontrollers should be ordered from left to right */
	public SimpleDriveTrain(int[] joysticks, SpeedController[] speedControllers) {
		controllerCount = speedControllers.length;
		int half = speedControllers.length / 2;
		left = new SpeedController[half];
		right = new SpeedController[half];
		for (int i = 0; i < left.length; i++) {
			left[i] = speedControllers[i];
		}
		for (int i = 0; i < right.length; i++) {
			right[i] = speedControllers[i + half];
		}
		sticks = new Joystick[joysticks.length];
		for (int i = 0; i < joysticks.length; i++) {
			sticks[i] = new Joystick(joysticks[i]);
		}
	}

	public SimpleDriveTrain(int[] joysticks, int fr, int fl, int br, int bl) {
		this(joysticks, new SpeedController[] { new CANTalon(fl),
				new CANTalon(bl), new CANTalon(fr), new CANTalon(br) });
	}

	public void invertLR() {
		invLR = !invLR;
	}

	public void invertFB() {
		invFB = !invFB;
	}

	public void update(double sensitivity) {
		double lr = 0, fb = 0;
		for (Joystick stick : sticks) {
			lr += stick.getX() * sensitivity;
			fb += stick.getY() * sensitivity;
		}
		lr = lr < -1 ? -1 : (lr > 1 ? 1 : lr) * (invLR ? -1 : 1);
		fb = fb < -1 ? -1 : (fb > 1 ? 1 : fb) * (invFB ? -1 : 1);
		double l = lr * fb, r = -lr * fb;
		for (SpeedController sc : left) {
			sc.set(l);
		}
		for (SpeedController sc : right) {
			sc.set(r);
		}
	}

}
