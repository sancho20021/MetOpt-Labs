package methods.multidimensional.newton.methods;

import methods.unidimensional.FibonacciMinimizer;
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
       protected Vector d;
       protected AdvancedMatrix g;

       protected QuasiNewtonianMinimizer(AnalyticFunction fun, Vector startX, double eps) {
              super(fun, startX, eps);
              restart();
       }

       @Override
       public boolean hasNext() {
              return s == null || s.getEuclideanNorm() > eps;
       }

       @Override
       protected Vector nextIteration() {
              // :SORRY: я не научился искать минимум функции
              double r = 0;
              // :TODO: r = argmin alpha (f(x + alpha * d))
              r = getArgMin(d, 0, 1000);
              s = d.multiply(r);
              x = x.add(s);
              Vector gy = gx;
              gx = fun.getGradient(x);
              Vector p = gx.subtract(gy);
              Vector v = g.multiply(p);
              g = getG(v, p, s);
              d = g.multiply(-1).multiply(gx);
              return x;
       }

       @Override
       public void restart() {
              x = startX;
              gx = fun.getGradient(x);
              d = gx.multiply(-1);
              g = new DiagonalMatrix(DoubleStream.of(1).limit(startX.size()).toArray());
       }

       @Override
       public Vector getCurrentXMin() {
              return x;
       }

       public abstract AdvancedMatrix getG(Vector v, Vector p, Vector s);

}
