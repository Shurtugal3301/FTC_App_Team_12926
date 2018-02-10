
public class NextGlyphCalculator {

    public enum NextGlyph {

        WHITE, BROWN, EITHER, COMPLETE

    }

    static char[][] currentCipher =
            {
                    {'N', 'N', 'N'},
                    {'N', 'N', 'N'},
                    {'N', 'N', 'N'},
                    {'N', 'B', 'N'}
            };

    static char[][][] solutions =
            {
                    {
                            {'W', 'B', 'W'},
                            {'B', 'W', 'B'},
                            {'W', 'B', 'W'},
                            {'B', 'W', 'B'}
                    },
                    {
                            {'B', 'W', 'B'},
                            {'W', 'B', 'W'},
                            {'B', 'W', 'B'},
                            {'W', 'B', 'W'}
                    },
                    {
                            {'W', 'B', 'W'},
                            {'B', 'W', 'B'},
                            {'B', 'W', 'B'},
                            {'W', 'B', 'W'}
                    },
                    {
                            {'B', 'W', 'B'},
                            {'W', 'B', 'W'},
                            {'W', 'B', 'W'},
                            {'B', 'W', 'B'}
                    },
                    {
                            {'B', 'W', 'W'},
                            {'B', 'B', 'W'},
                            {'W', 'B', 'B'},
                            {'W', 'W', 'B'}
                    },
                    {
                            {'W', 'B', 'B'},
                            {'W', 'W', 'B'},
                            {'B', 'W', 'W'},
                            {'B', 'B', 'W'}
                    }
            };

    static boolean[] isPossible = new boolean[6];

    public static NextGlyph[] nextGlyphs = new NextGlyph[3];

    public static void main(String[] args) {

        SolveCipher();

    }

    public static void SolveCipher() {

        CheckCipher();

        NextPiece();

    }

    private static void NextPiece() {

        for (int k = 2; k >= 0; k--) {

            boolean needWhite = false;
            boolean needBrown = false;

            boolean hasValue = false;

            for (int j = 3; j >= 0; j--) {

                if (currentCipher[j][k] == 'N' && !hasValue) {

                    for (int i = 5; i >= 0; i--) {

                        if (isPossible[i]) {

                            try {

                                if (solutions[i][j][k] == 'W')
                                    needWhite = true;

                                if (solutions[i][j][k] == 'B')
                                    needBrown = true;

                            } catch (ArrayIndexOutOfBoundsException e) {

                                if (e != null) {

                                    // Column completed
                                    System.out.println("Column completed!");

                                }

                            }

                        }

                    }

                    hasValue = true;

                }

            }

            if (!needWhite && !needBrown) {

                nextGlyphs[k] = NextGlyph.COMPLETE;

            } else if (needWhite && needBrown) {

                nextGlyphs[k] = NextGlyph.EITHER;

            } else if (needWhite && !needBrown) {

                nextGlyphs[k] = NextGlyph.WHITE;

            } else if (!needWhite && needBrown) {

                nextGlyphs[k] = NextGlyph.BROWN;

            }

        }

        System.out.println("");

        for (int i = 0; i < nextGlyphs.length; i++) {

            String string = "";

            switch (nextGlyphs[i]) {

                case WHITE:
                    string = "White";
                    break;

                case BROWN:
                    string = "Brown";
                    break;

                case EITHER:
                    string = "Either";
                    break;

                case COMPLETE:
                    string = "Complete";
                    break;

            }

            System.out.println("Column " + (i + 1) + ": " + string);

        }

    }

    private static void CheckCipher() {

        System.out.println("Current Cipher:");

        for (int j = 3; j >= 0; j--) {

            for (int k = 2; k >= 0; k--) {

                System.out.print(" " + currentCipher[j][k] + " ");

            }

            System.out.println("");

        }

        System.out.println("");

        for (int l = 0; l < isPossible.length; l++) {

            isPossible[l] = true;

        }

        for (int i = 0; i < 6; i++) {

            for (int j = 0; j < 4; j++) {

                for (int k = 0; k < 3; k++) {

                    if (currentCipher[j][k] != 'N') {

                        if (currentCipher[j][k] != solutions[i][j][k]) {

                            isPossible[i] = false;

                        }

                    }

                }

            }

        }

        for (int i = 0; i < isPossible.length; i++) {

            if (isPossible[i]) {

                System.out.println("Solution " + (i + 1) + " is possible.");

                for (int j = 0; j < 4; j++) {

                    for (int k = 0; k < 3; k++) {

                        System.out.print(" " + solutions[i][j][k] + " ");

                    }

                    System.out.println("");

                }

                System.out.println("");

            }

        }

    }

}
