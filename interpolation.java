public class interpolation {
    public static final int taille = 63;

    private static ColorRGB topLeft = new ColorRGB(255, 0, 0);    // Rouge
    private static ColorRGB topRight = new ColorRGB(0, 255, 0);   // Vert
    private static ColorRGB bottomLeft = new ColorRGB(0, 0, 255); // Bleu
    private static ColorRGB bottomRight = new ColorRGB(255, 255, 0); // Jaune

    public static void main(String[] args) {
        // Créer et afficher l'interface graphique avec les couleurs globales
        Gui.createAndShowGui(topLeft, topRight, bottomLeft, bottomRight);

        // Créer une instance pour utiliser les méthodes non statiques
        interpolation interp = new interpolation();
        interp.quadriRadialInterpolation();
    }

    private ColorRGB moyenne(ColorRGB val1, ColorRGB val2) {
        int r = (val1.rouge + val2.rouge) / 2;
        int g = (val1.vert + val2.vert) / 2;
        int b = (val1.bleu + val2.bleu) / 2;
        return new ColorRGB(r, g, b);
    }

    private ColorRGB centre() {
        return moyenne(moyenne(topRight, bottomLeft), moyenne(bottomRight, topLeft));
    }

    private ColorRGB interpolateColor(ColorRGB color1, ColorRGB color2, double indice) {
        int r = (int) (color1.getRouge() + indice * (color2.getRouge() - color1.getRouge()));
        int g = (int) (color1.getVert() + indice * (color2.getVert() - color1.getVert()));
        int b = (int) (color1.getBleu() + indice * (color2.getBleu() - color1.getBleu()));
        return new ColorRGB(r, g, b);
    }

    private void diagonalInterpolation(int x1, int y1, int x2, int y2) {
        ColorRGB color1 = Gui.getColorAt(x1, y1);
        ColorRGB color2 = Gui.getColorAt(x2, y2);

        int dx = Integer.signum(x2 - x1);
        int dy = Integer.signum(y2 - y1);
        int steps = Math.abs(x2 - x1); // ou y2 - y1, c'est égal sur une diagonale

        for (int i = 0; i <= steps; i++) {
            double ratio = i / (double) steps;
            ColorRGB interpolated = interpolateColor(color1, color2, ratio);
            Gui.updateCell(x1 + i * dx, y1 + i * dy, interpolated);
        }
    }

    private void linearInterpolation(int x1, int y1, int x2, int y2) {
        ColorRGB color1 = Gui.getColorAt(x1, y1);
        ColorRGB color2 = Gui.getColorAt(x2, y2);

        if (x1 == x2) { // Verticale
            for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                double ratio = (y - y1) / (double) (y2 - y1);
                ColorRGB color = interpolateColor(color1, color2, ratio);
                Gui.updateCell(x1, y, color);
            }
        } else if (y1 == y2) { // Horizontale
            for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                double ratio = (x - x1) / (double) (x2 - x1);
                ColorRGB color = interpolateColor(color1, color2, ratio);
                Gui.updateCell(x, y1, color);
            }
        }
    }

    private void triangleFill(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            int var = (x1 == 0) ? 1 : -1;
            for (int i = 0; i < (taille - 1) / 2; i++) {
                linearInterpolation(x1 + (i * var), y1 + i, x2 + (i * var), y2 - i);
            }
        } else {
            int var = (y1 == 0) ? 1 : -1;
            for (int i = 0; i < (taille - 1) / 2; i++) {
                linearInterpolation(x1 + i, y1 + (i * var), x2 - i, y2 + (i * var));
            }
        }
    }

    public void quadriRadialInterpolation() {
        Gui.updateCell((taille - 1) / 2, (taille - 1) / 2, centre());

        diagonalInterpolation(0, 0, (taille - 1) / 2, (taille - 1) / 2);
        diagonalInterpolation((taille - 1) / 2, (taille - 1) / 2, 0, taille - 1);
        diagonalInterpolation((taille - 1) / 2, (taille - 1) / 2, taille - 1, 0);
        diagonalInterpolation((taille - 1) / 2, (taille - 1) / 2, taille - 1, taille - 1);

        triangleFill(0, 0, 0, taille - 1);
        triangleFill(0, 0, taille - 1, 0);
        triangleFill(taille - 1, 0, taille - 1, taille - 1);
        triangleFill(0, taille - 1, taille - 1, taille - 1);
    }

    public void bilinearInterpolation(){
        //tracer les colonnes droites et gauches
        linearInterpolation(0,0,taille-1,0);
        linearInterpolation(0,taille-1,taille-1,taille-1);

        //relier les lignes
        for (int i=0;i<taille;i++){
            linearInterpolation(i,0,i,taille-1);
        }
    }
}
