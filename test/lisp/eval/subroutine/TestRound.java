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

public class TestRound {
    Reader createReader(String stdin) {
        StringReader sr = new StringReader(stdin);
        BufferedReader br = new BufferedReader(sr);
        return new Reader(br);
    }

    @Test
    public void roundの束縛() throws IOException, LispException {
        String stdin = "round";

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
            String stdin = "(round)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

            SExpression expected = Int.valueOf(0);
            assertThat(actual, is(expected));

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続きroundの引数は1個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void 引数が1個() throws IOException, LispException {
        String stdin = "(round 1.23)";

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
            String stdin = "(round 1 2)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

            SExpression expected = Int.valueOf(0);
            assertThat(actual, is(expected));

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続きroundの引数は1個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test(expected = LispException.class)
    public void 数値以外の入力() throws IOException, LispException {
        try {
            String stdin = "(round 'num)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

            SExpression expected = Int.valueOf(0);
            assertThat(actual, is(expected));

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続きroundの引数は数値である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
