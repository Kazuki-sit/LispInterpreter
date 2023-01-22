package lisp.eval.subroutine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import lisp.SetUp;
import lisp.eval.*;
import lisp.eval.Float;
import lisp.exception.LispException;
import lisp.reader.Reader;

public class TestMoreEqual {
    Reader createReader(String stdin) {
        StringReader sr = new StringReader(stdin);
        BufferedReader br = new BufferedReader(sr);
        return new Reader(br);
    }

    @Test
    public void moreequalの束縛() throws IOException, LispException {
        String stdin = ">=";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);
    }

    @Test(expected = LispException.class)
    public void 引数が0個() throws IOException, LispException {
        try {
            String stdin = "(>=)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続き>=の引数は2個以上である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test(expected = LispException.class)
    public void 引数が1個() throws IOException, LispException {
        try {
            String stdin = "(>= 1)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続き>=の引数は2個以上である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void 引数が2個() throws IOException, LispException {
        String stdin = "(>= 1 2)";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);

        SExpression expected = Bool.valueOf(false);
        assertThat(actual, is(expected));
    }

    @Test
    public void 引数が3個() throws IOException, LispException {
        String stdin = "(>= 3 2 2)";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);

        SExpression expected = Bool.valueOf(true);
        assertThat(actual, is(expected));
    }

    @Test
    public void IntとFloatの比較() throws IOException, LispException {
        String stdin = "(>= 4 3.0)";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);

        SExpression expected = Bool.valueOf(false);
        assertThat(actual, is(expected));
    }

    @Test(expected = LispException.class)
    public void 数値以外() throws IOException, LispException {
        try {
            String stdin = "(>= 'x 'x)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続き>=の引数は数値である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
