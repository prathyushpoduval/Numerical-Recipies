package com.nr.la;

import static java.lang.Math.*;

public class GaussJordan {
  private GaussJordan(){}
  
  /**
   * Gauss-Jordan decomposition - PTC
   * 
   * 
   * Linear equation solution by Gauss-Jordan elimination.
   * The input matrix is a[0..n-1][0..n-1]. b[0..n-1][0..m-1] is
   * input containing the m right-hand side vectors. On output,
   * a is replaced by its matrix inverse, and b is replaced by
   * the corresponding set of solution vectors.
   * 
   * 
 * Copyright (C) Numerical Recipes Software 1986-2007
 * Java translation Copyright (C) Huang Wen Hui 2012
 *
   * @author hwh
   *
   */
  public static void gaussj(final double[][] a, final double[][] b) {
    int i,icol=0,irow=0,j,k,l,ll,n=a.length,m=b[0].length;
    double big,dum,pivinv;
    // These integer arrays are used for bookkeeping on the pivoting
    int[] indxc = new int[n];
    int[] indxr = new int[n];
    int[] ipiv = new int[n];
    for (j=0;j<n;j++) ipiv[j]=0;
    // This is the main loop over the columns to be reduced.
    for (i=0;i<n;i++) {
      big=0.0;
      // This is the outer loop of the search for a pivot element.
      for (j=0;j<n;j++)
        if (ipiv[j] != 1)
          for (k=0;k<n;k++) {
            if (ipiv[k] == 0) {
              if (abs(a[j][k]) >= big) {
                big=abs(a[j][k]);
                irow=j;
                icol=k;
              }
            }
          }
      ++(ipiv[icol]);
      /*
       * We now have the pivot element, so we interchange rows, if needed, to put the pivot
       * element on the diagonal. The columns are not physically interchanged, only relabeled:
       * indxc[i], the column of the .i C 1/th pivot element, is the .i C 1/th column that is
       * reduced, while indxr[i] is the row in which that pivot element was originally located.
       * If indxr[i] ¤ indxc[i], there is an implied column interchange. With this form of
       * bookkeeping, the solution b's will end up in the correct order, and the inverse matrix
       * will be scrambled by columns.
       * 
       */
      if (irow != icol) {
        for (l=0;l<n;l++) {
          // SWAP(a[irow][l],a[icol][l]);
          double swap = a[irow][l]; a[irow][l]=a[icol][l]; a[icol][l] = swap; 
        }
        for (l=0;l<m;l++) {
          // SWAP(b[irow][l],b[icol][l]);
          double swap = b[irow][l]; b[irow][l] = b[icol][l]; b[icol][l] = swap;
        }
      }
      /*
       * We are now ready to divide the pivot row by the 
       * pivot element, located at irow and icol.
       */
      indxr[i]=irow;
      indxc[i]=icol;
      if (a[icol][icol] == 0.0) throw new IllegalArgumentException("gaussj: Singular Matrix");
      pivinv=1.0/a[icol][icol];
      a[icol][icol]=1.0;
      for (l=0;l<n;l++) a[icol][l] *= pivinv;
      for (l=0;l<m;l++) b[icol][l] *= pivinv;
      /*
       * Next, we reduce the rows..., except for the pivot one, of course.
       * 
       */
      for (ll=0;ll<n;ll++)
        if (ll != icol) {
          dum=a[ll][icol];
          a[ll][icol]=0.0;
          for (l=0;l<n;l++) a[ll][l] -= a[icol][l]*dum;
          for (l=0;l<m;l++) b[ll][l] -= b[icol][l]*dum;
        }
    }
    for (l=n-1;l>=0;l--) {
      if (indxr[l] != indxc[l])
        for (k=0;k<n;k++) {
          // SWAP(a[k][indxr[l]],a[k][indxc[l]]);
          double swap = a[k][indxr[l]]; a[k][indxr[l]] = a[k][indxc[l]]; a[k][indxc[l]] = swap;
        }
    }
  }

  /**
   * Overloaded version with no right-hand sides. Replaces a by its inverse.
   * 
   * @param a
   */
  public static void gaussj(final double[][] a) {
    double[][] b =new double[a.length][0]; // Dummy vector with zero columns.
    gaussj(a,b);
  }
}
