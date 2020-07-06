package discreteEventSimulation.command;

// 2a
@FunctionalInterface
public interface Command {
	public void execute() throws Exception;
}
