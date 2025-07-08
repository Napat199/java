class Complex {
    private double r, i;
    Complex(double r, double i) {
        this.r = r; this.i = i;
    }
    Complex(Complex c) {
        this(c.r, c.i);
    }
    public void add(Complex c) {
        r += c.r;
        i += c.i;
    }
    public void print() {
        System.out.println(r + "+ i" + i);
    }
    public void minus(Complex c){
        r -= c.r;
        i -= c.i;
    }
    public void muti(Complex c){
        double real = r * c.r - i * c.i;
        double imag = r * c.i + i * c.r;
        r = real;
        i = imag;
    }
    public void divi(Complex c){
        double denominator = c.r * c.r + c.i * c.i;
        double real = (r * c.r + i * c.i) / denominator;
        double imag = (i * c.r - r * c.i) / denominator;
        r = real;
        i = imag;
    }
}
class ComplexTest {
    public static void main(String args[]) {
        Complex a = new Complex(1.0, 2.0);
        Complex b = new Complex(3.0, 4.0);
        Complex c = new Complex(a);
        System.out.print("Plus: ");
        c.add(b);
        c.print();

        c = new Complex(a);
        System.out.print("Minus: ");
        c.minus(b);
        c.print();

        c = new Complex(a);
        System.out.print("Multiply: ");
        c.muti(b);
        c.print();

        c = new Complex(a);
        System.out.print("Divide: ");
        c.divi(b);
        c.print();
    }
}
