package com.nr.la;

public class Toepltz {
  private Toepltz(){}

  /**
   * Solves the Toeplitz system. - PTC
   * 
   * The Toeplitz matrix need not be symmetric.
   * y[0..n-1] and r[0..2*n-2] are input arrays; x[0..n-1] is the output array.
   * 
   * @param r
   * @param x
   * @param y
   */
  public static void toeplz(final double[] r, final double[] x, final double[] y) {
    int j,k,m,m1,m2,n1,n=y.length;
    double pp,pt1,pt2,qq,qt1,qt2,sd,sgd,sgn,shn,sxn;
    n1=n-1;
    if (r[n1] == 0.0) throw new IllegalArgumentException("toeplz-1 singular principal minor");
    x[0]=y[0]/r[n1];
    if (n1 == 0) return;
    double[] g = new double[n1];
    double[] h = new double[n1];
    g[0]=r[n1-1]/r[n1];
    h[0]=r[n1+1]/r[n1];
    for (m=0;m<n;m++) {
      m1=m+1;
      sxn = -y[m1];
      sd = -r[n1];
      for (j=0;j<m+1;j++) {
        sxn += r[n1+m1-j]*x[j];
        sd += r[n1+m1-j]*g[m-j];
      }
      if (sd == 0.0) throw new IllegalArgumentException("toeplz-2 singular principal minor");
      x[m1]=sxn/sd;
      for (j=0;j<m+1;j++)
        x[j] -= x[m1]*g[m-j];
      if (m1 == n1) return;
      sgn = -r[n1-m1-1];
      shn = -r[n1+m1+1];
      sgd = -r[n1];
      for (j=0;j<m+1;j++) {
        sgn += r[n1+j-m1]*g[j];
        shn += r[n1+m1-j]*h[j];
        sgd += r[n1+j-m1]*h[m-j];
      }
      if (sgd == 0.0) throw new IllegalArgumentException("toeplz-3 singular principal minor");
      g[m1]=sgn/sgd;
      h[m1]=shn/sd;
      k=m;
      m2=(m+2) >> 1;
      pp=g[m1];
      qq=h[m1];
      for (j=0;j<m2;j++) {
        pt1=g[j];
        pt2=g[k];
        qt1=h[j];
        qt2=h[k];
        g[j]=pt1-pp*qt2;
        g[k]=pt2-pp*qt1;
        h[j]=qt1-qq*pt2;
        h[k--]=qt2-qq*pt1;
      }
    }
    throw new IllegalArgumentException("toeplz - should not arrive here!");
  }

}
