package mastermind_ai;

public class TestClient {

	public static void main(String[] args) {

		Computer HAL = Computer.getInstance();
		int totalTurns = 0;
		for (int i = 0; i < 20; i++) {
			HAL.init();
			String testcase = HAL.generateSecret();
			HAL.setOpponentSecret(testcase);
			System.out.println("Testing against " + testcase + "...");
			int turns = 0;
			while (true) {
				String guess = HAL.makeGuess();
				turns++;
				System.out.println(guess);
				if (guess.equals(testcase)) {
					System.out.printf("Win in %d turns.\n", turns);
					break;
				}
			}
			totalTurns += turns;
		}
		double averageTurns = (double) totalTurns / 20;
		System.out.printf("Done testing.\nAverage win in %.2f turns.\n",
				averageTurns);
	}
}
