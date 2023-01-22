package lisp.eval.specialform;

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

public class TestOr {
    Reader createReader(String stdin) {
        StringReader sr = new StringReader(stdin);
        BufferedReader br = new BufferedReader(sr);
        return new Reader(br);
    }

    @Test
    public void orの束縛() throws IOException, LispException {
        String stdin = "or";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);
    }

    @Test
    public void 引数が0個() throws IOException, LispException {
        String stdin = "(or)";

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
    public void 引数が1個() throws IOException, LispException {
        String stdin = "(or (= 1 2))";

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
    public void 引数が2個() throws IOException, LispException {
        String stdin = "(or (= 1 1) (= 2 3))";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);

        SExpression expected = Bool.valueOf(true);
        assertThat(actual, is(expected));
    }

    @Test(expected = LispException.class)
    public void 引数が真偽値でない() throws IOException, LispException {
        try {
            String stdin = "(or (+ 1 2))";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("特殊形式orの引数の評価値は真偽値である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
