package lisp.eval.specialform;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.junit.Test;

import lisp.SetUp;
import lisp.eval.*;
import lisp.exception.LispException;
import lisp.reader.Reader;

public class TestIf {
    Reader createReader(String stdin) {
        StringReader sr = new StringReader(stdin);
        BufferedReader br = new BufferedReader(sr);
        return new Reader(br);
    }

    @Test
    public void ifの束縛() throws IOException, LispException {
        String stdin = "if";

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
            String stdin = "(if)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("特殊形式ifの引数は2個か3個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test(expected = LispException.class)
    public void 引数が1個() throws IOException, LispException {
        try {
            String stdin = "(if #t)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("特殊形式ifの引数は2個か3個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void 引数が2個() throws IOException, LispException {
        String stdin = "(if #t (+ 1 2))";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);

        SExpression expected = Int.valueOf(3);
        assertThat(actual, is(expected));
    }

    @Test
    public void 引数が3個() throws IOException, LispException {
        String stdin = "(if #f (+ 1 2) (* 3 4))";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);

        SExpression expected = Int.valueOf(12);
        assertThat(actual, is(expected));
    }

    @Test(expected = LispException.class)
    public void 引数が4個() throws IOException, LispException {
        try {
            String stdin = "(if #f (+ 1 2) (* 3 4) (/ 5 6))";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("特殊形式ifの引数は2個か3個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void 未定義値が返ってくる() throws IOException, LispException {
        String stdin = "(if #f (+ 1 2))";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);

        SExpression expected = Undef.getInstance();
        assertThat(actual, is(expected));
    }
}
