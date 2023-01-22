package lisp;

import lisp.eval.Environment;
import lisp.eval.Evaluator;
import lisp.eval.SExpression;
import lisp.exception.EndOfFileException;
import lisp.exception.LispException;
import lisp.reader.Reader;
import org.junit.Test;

import java.io.*;

public class TestMain {
    @Test
    public void タートルグラフィックス() throws IOException, LispException {
        Environment env = SetUp.getGlobalEnvironment();
        String path = "lisp\\KochCurve.scm";

        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            Reader reader = new Reader(in);

            while (true) {
                try {
                    SExpression exp = reader.read();
                    Evaluator.eval(exp, env);

                } catch (EndOfFileException e) {
                    break;
                } catch (LispException e) {
                    System.err.println(e.getMessage());
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
