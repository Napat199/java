import java.util.Scanner;

class square {
    double x, y, width, height;

    public square(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double left() {
        return x - width / 2;
    }

    public double right() {
        return x + width / 2;
    }

    public double top() {
        return y + height / 2;
    }

    public double bottom() {
        return y - height / 2;
    }

    public boolean contains(square r) {
        return r.left() >= this.left() && r.right() <= this.right() &&
               r.bottom() >= this.bottom() && r.top() <= this.top();
    }

    public boolean overlaps(square r) {
        return this.left() < r.right() &&
               this.right() > r.left() &&
               this.top() > r.bottom() &&
               this.bottom() < r.top();
    }
}

class testsquare {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        System.out.print("Enter center x y width height of rectangle 1: ");
        double x1 = input.nextDouble();
        double y1 = input.nextDouble();
        double w1 = input.nextDouble();
        double h1 = input.nextDouble();

        System.out.print("Enter center x y width height of rectangle 2: ");
        double x2 = input.nextDouble();
        double y2 = input.nextDouble();
        double w2 = input.nextDouble();
        double h2 = input.nextDouble();

        square r1 = new square(x1, y1, w1, h1);
        square r2 = new square(x2, y2, w2, h2);

        if (r1.contains(r2)) {
            System.out.println("square 2 is inside square 1");
        } else if (r1.overlaps(r2)) {
            System.out.println("square 2 overlaps square 1");
        } else {
            System.out.println("square 2 does not overlap square 1");
        }

        input.close();
    }
}
