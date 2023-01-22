package lisp.eval.subroutine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import lisp.SetUp;
import lisp.eval.*;
import lisp.exception.LispException;
import lisp.reader.Reader;

public class TestCar{
    Reader createReader(String stdin) {
        StringReader sr = new StringReader(stdin);
        BufferedReader br = new BufferedReader(sr);
        return new Reader(br);
    }

    @Test
    public void carの束縛() throws IOException, LispException {
        String stdin = "car";

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
            String stdin = "(car)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続きcarの引数は1個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void 引数が1個() throws IOException, LispException {
        String stdin = "(car '(1 2 3))";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);

        SExpression expected = Int.valueOf(1);
        assertThat(actual, is(expected));
    }

    @Test(expected = LispException.class)
    public void 引数が2個() throws IOException, LispException {
        try {
            String stdin = "(car '(1 2 3) '(4 5))";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続きcarの引数は1個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test(expected = LispException.class)
    public void 引数がドット対でない() throws IOException, LispException {
        try {
            String stdin = "(car 1)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続きcarの引数はドット対である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test(expected = LispException.class)
    public void 空リストのCar() throws IOException, LispException {
        String sexp = "(car . ((quote . (() . ())) . ()))"; // (car '())
        Environment env = SetUp.getGlobalEnvironment();

        Reader reader = createReader(sexp);
        SExpression exp = reader.read();
        SExpression actual = Evaluator.eval(exp, env); // throws LispException
    }

    @Test
    public void ドット対のCar() throws IOException, LispException {
        String sexp = "(car . ((quote . ((a . b) . ())) . ()))"; // (car '(a . b))
        Environment env = SetUp.getGlobalEnvironment();

        Reader reader = createReader(sexp);
        SExpression exp = reader.read();
        SExpression actual = Evaluator.eval(exp, env);

        SExpression expected = Symbol.getInstance("a"); // a
        assertThat(actual, is(expected));
    }
}
