public class Team {

	private String name;
	private int stars;
	private int intlParticipations;
	private int intlFinals;
	private int localFinals;
	private long fans;
	private double marketValue;
	private double payroll;

	public Team() {}

	public Team(String name, int stars, int intlParticipations, 
		int intlFinals, int localFinals, long fans, 
		double marketValue, double payroll) {

		this.name = name;
		this.stars = stars;
		this.intlParticipations = intlParticipations;
		this.intlFinals = intlFinals;
		this.localFinals = localFinals;
		this.fans = fans;
		this.marketValue = marketValue;
		this.payroll = payroll;
	}

	public final static void sortByStars(Team[] teams) {
		int N = teams.length;
		int i = 1;

		while(i < N) {
			Team current = teams[i];
			int currentKey = teams[i].stars;
			int j=i-1;

			while(j>=0 && teams[j].stars > currentKey) {
				teams[j+1] = teams[j];
				j = j-1;
			}
			teams[j+1] = current;
			i = i + 1;
		}
	}

	//TODO insertion
	public final static void sortByintlParticipations(Team[] teams) {
		int N = teams.length;
		for (int i = 1; i < N; i++) {
			Team current = teams[i];
			int currentKey = current.intlParticipations;
			int j = i - 1;
			while (j >= 0 && teams[j].intlParticipations > currentKey) {
				teams[j + 1] = teams[j];
				j = j - 1;
			}
			teams[j + 1] = current;
		}
	}

	//TODO insertion
	public final static void sortByintlFinals(Team[] teams) {
		int N = teams.length;
		for (int i = 1; i < N; i++) {
			Team current = teams[i];
			int currentKey = current.intlFinals;
			int j = i - 1;
			while (j >= 0 && teams[j].intlFinals > currentKey) {
				teams[j + 1] = teams[j];
				j = j - 1;
			}
			teams[j + 1] = current;
		}
	}

	//TODO Bubble
	public final static void sortBylocalFinals(Team[] teams) {
		int N = teams.length;
		for (int i = 0; i < N - 1; i++) {
			for (int j = 0; j < N - 1 - i; j++) {
				if (teams[j].localFinals > teams[j + 1].localFinals) {
					Team tmp = teams[j];
					teams[j] = teams[j + 1];
					teams[j + 1] = tmp;
				}
			}
		}
	}

	//TODO Bubble
	public final static void sortByFans(Team[] teams) {
		int N = teams.length;
		for (int i = 0; i < N - 1; i++) {
			for (int j = 0; j < N - 1 - i; j++) {
				if (teams[j].fans > teams[j + 1].fans) {
					Team tmp = teams[j];
					teams[j] = teams[j + 1];
					teams[j + 1] = tmp;
				}
			}
		}
	}

	//TODO Selection
	public final static void sortByMarketValue(Team[] teams) {
		int N = teams.length;
		for (int i = 0; i < N - 1; i++) {
			int minIndex = i;
			for (int j = i + 1; j < N; j++) {
				if (teams[j].marketValue < teams[minIndex].marketValue) {
					minIndex = j;
				}
			}
			if (minIndex != i) {
				Team tmp = teams[i];
				teams[i] = teams[minIndex];
				teams[minIndex] = tmp;
			}
		}
	}

	//TODO Selection
	public final static void sortByPayroll(Team[] teams) {
		int N = teams.length;
		for (int i = 0; i < N - 1; i++) {
			int minIndex = i;
			for (int j = i + 1; j < N; j++) {
				if (teams[j].payroll < teams[minIndex].payroll) {
					minIndex = j;
				}
			}
			if (minIndex != i) {
				Team tmp = teams[i];
				teams[i] = teams[minIndex];
				teams[minIndex] = tmp;
			}
		}
	}


	public static final void printer(Team[] teams) {
        String header = String.format(
                "%-22s %6s %6s %8s %9s %12s %14s %12s",
                "Team","Stars","Intl","IntlFin","LocalFin","Fans","MarketValue","Payroll"
        );
        String sep = "-".repeat(header.length());
        System.out.println(header);
        System.out.println(sep);

        for (Team t : teams) {
            String fansStr = java.text.NumberFormat
                    .getInstance(java.util.Locale.US)
                    .format(t.fans); // 1,234,567
            System.out.printf("%-22s %6d %6d %8d %9d %12s %14s %12s%n",
                    t.name,
                    t.stars,
                    t.intlParticipations,
                    t.intlFinals,
                    t.localFinals,
                    fansStr,
                    String.format("%.2fM", t.marketValue),
                    String.format("%.2fM", t.payroll)
            );
        }
        System.out.println(sep);
    }


	public final static void main(String[] args) {

		Team t1 = new Team("Once Caldas",         4, 3, 3,  6, 1_800_000L, 11.5,  4.5);
        Team t2 = new Team("Atlético Nacional",  18, 7,13, 30,11_591_000L, 25.3, 12.0);
        Team t3 = new Team("Millonarios",        16, 4, 2, 26, 8_996_000L, 15.8, 10.0);

        Team[] teams = {t1, t2, t3};

        System.out.println("Ordenado por Estrellas:");
        sortByStars(teams);
        printer(teams);

        System.out.println("Ordenado por Participaciones Internacionales:");
        sortByintlParticipations(teams);
        printer(teams);

        System.out.println("Ordenado por Finales Internacionales:");
        sortByintlFinals(teams);
        printer(teams);

        System.out.println("Ordenado por Finales Locales:");
        sortBylocalFinals(teams);
        printer(teams);

        System.out.println("Ordenado por Fans:");
        sortByFans(teams);
        printer(teams);

        System.out.println("Ordenado por Valor de Mercado:");
        sortByMarketValue(teams);
        printer(teams);

        System.out.println("Ordenado por Nómina (Payroll):");
        sortByPayroll(teams);
        printer(teams);
	}
}
