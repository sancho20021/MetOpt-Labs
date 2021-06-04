package methods.multidimensional.newton.methods;

import models.Vector;
import models.functions.AnalyticFunction;
import models.matrices.AdvancedMatrix;
import models.matrices.DiagonalMatrix;

import java.util.stream.DoubleStream;

/**
 * @author Yaroslav Ilin
 */
public abstract class QuasiNewtonianMinimizer extends NewtonMinimizer {
       protected Vector gx;
       protected Vector w;
       protected AdvancedMatrix g;

       protected QuasiNewtonianMinimizer(AnalyticFunction fun, Vector startX, double eps) {
              super(fun, startX, eps);
              restart();
       }

       @Override
       public boolean hasNext() {
              return dx == null || dx.getEuclideanNorm() > eps;
       }

       @Override
       protected Vector nextIteration() {
              double r = getArgMin(w, 0, 1000);
              dx = w.multiply(r);
              x = x.add(dx);
              Vector gPrev = gx;
              gx = fun.getGradient(x);
              Vector deltaG = gx.subtract(gPrev);
              Vector v = g.multiply(deltaG);
              g = getG(v, deltaG, dx);
              w = g.multiply(-1).multiply(gx);
              return x;
       }

       @Override
       public void restart() {
              x = startX;
              gx = fun.getGradient(x);
              w = gx.multiply(-1);
              g = new DiagonalMatrix(DoubleStream.generate(() -> 1).limit(startX.size()).toArray());
              dx = null;
       }

       @Override
       public Vector getCurrentXMin() {
              return x;
       }

       public abstract AdvancedMatrix getG(Vector v, Vector p, Vector s);

}
