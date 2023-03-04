
package frc.robot.commands;
import frc.robot.subsystems.CargoDisplay;


public class DisplayCone extends CargoDisplayCommand {

    public DisplayCone(CargoDisplay subsystem) {
        super("C", subsystem);
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
