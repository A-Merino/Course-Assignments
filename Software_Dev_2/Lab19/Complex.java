/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 3, Fall 2022
 * Project: Newtons Chaos
*/

public final class Complex {
   // object data types
   private final double real;
   private final double imag;

   public Complex (final double rVal, final double iVal) { // canonical constructor
      this.real = rVal;
      this.imag = iVal;
   }

   public Complex () { // if nothing it is 0
      this(0.0, 0.0);
   }

   public static void main(String[] args) {
      Complex c = new Complex(1, -2);
      Complex c1 = new Complex(4, -3);
      Complex c2 = new Complex(2, -9);
      Complex c3 = new Complex(7, -3.3);

      System.out.println(c.abs());
      System.out.println(c1.plus(c2));
      System.out.println(c1.times(c2));
      System.out.println(c1.minus(c2));
      System.out.println(c1.divides(c2));
   }

   // toString() method Ex: 3 + 4i
   @java.lang.Override
   public String toString () {
      if (this.imag > 0) {
         return String.format("%.2f + %.2fi", this.real, this.imag);     
      }
      return String.format("%.2f - %.2fi", this.real, Math.abs(this.imag));
   }

   public double getReal () { // getter method for real number
      return this.real;
   }

   public double getImaginary () { // getter method for imaginary number
      return this.imag;
   }

   public double abs () { // grabs actual value of number
      return Math.hypot(this.real, this.imag);
   }

   public Complex plus (final Complex num) { // adds together complex numbers
      final double plusReal = this.real + num.real;
      final double plusImag = this.imag + num.imag;
      return new Complex(plusReal, plusImag);
   }

   public Complex minus (final Complex num) { // subtracts two complex numbers
      final double minusReal = this.real - num.real;
      final double minusImag = this.imag - num.imag;
      return new Complex(minusReal, minusImag);
   }

   public Complex times (final Complex num) { // uses Foil method to multiply
      final double timesReal = (this.real * num.real) - (this.imag * num.imag);
      final double timesImag = (this.imag * num.real) + (this.real * num.imag);
      return new Complex(timesReal, timesImag);
   }

   public Complex divides (final Complex num) { // divides two complex numbers
      final double scale = (num.real * num.real) + (num.imag * num.imag);
      final Complex con = num.conjugate();
      final Complex recip = new Complex(con.real / scale, con.imag / scale);
      return this.times(recip);
   }

   public Complex conjugate () { // returns conjucate of input
      return new Complex(this.real, -this.imag);
   }

   public double theta () { // angle to x-axis
      return Math.atan2(this.imag, this.real);
   }

   public Complex power (final int num) { // calculates powers of complex numbers
      Complex out = this;
      Complex first = this;
      for (int j = 0; j < num - 1; j++) {
         out = out.times(first);
      }
      return out;
   }


}
