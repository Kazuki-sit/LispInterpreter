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

public class TestLet {
    Reader createReader(String stdin) {
        StringReader sr = new StringReader(stdin);
        BufferedReader br = new BufferedReader(sr);
        return new Reader(br);
    }

    @Test
    public void lambdaの束縛() throws IOException, LispException {
        String stdin = "let";

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
            String stdin = "(let)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("特殊形式letの引数は2個以上である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test(expected = LispException.class)
    public void 引数が1個() throws IOException, LispException {
        try {
            String stdin = "(let ((p 10) (q 20)))";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("特殊形式letの引数は2個以上である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test(expected = LispException.class)
    public void 引数が2個() throws IOException, LispException {
        try {
            String stdin = "(let ((p 10) (q 20)) (+ p q x))";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("記号x に束縛された値がありません"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test(expected = LispException.class)
    public void 引数が3個() throws IOException, LispException {
        try {
            String stdin = "(let ((p 10) (q 20)) (+ p q x) (* p q x))";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("記号x に束縛された値がありません"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void 局所変数の記述() throws IOException, LispException {
        ArrayList<String> stdList = new ArrayList<>();
        stdList.add("(define x 1)");
        stdList.add("(define (f x) (let ((p 10) (q 20)) (+ p q x)))");
        stdList.add("(f 30)");

        Environment env = SetUp.getGlobalEnvironment();
        SExpression actual = null;

        for (String stdin : stdList) {
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);
        }

        SExpression expected = Int.valueOf(60);
        assertThat(actual, is(expected));
    }

    @Test
    public void 引数を持たない局所変数の使用() throws IOException, LispException {
        ArrayList<String> stdList = new ArrayList<>();
        stdList.add("(define x (let ((y 1000)) (lambda () y)))");
        stdList.add("(x)");

        Environment env = SetUp.getGlobalEnvironment();
        SExpression actual = null;

        for (String stdin : stdList) {
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);
        }

        SExpression expected = Int.valueOf(1000);
        assertThat(actual, is(expected));
    }
}
