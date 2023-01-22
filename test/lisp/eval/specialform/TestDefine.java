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

public class TestDefine {
    Reader createReader(String stdin) {
        StringReader sr = new StringReader(stdin);
        BufferedReader br = new BufferedReader(sr);
        return new Reader(br);
    }

    @Test
    public void defineの束縛() throws IOException, LispException {
        String stdin = "define";

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
            String stdin = "(define)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("特殊形式defineの引数は2個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test(expected = LispException.class)
    public void 引数が1個() throws IOException, LispException {
        try {
            String stdin = "(define x)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("特殊形式defineの引数は2個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void 引数が2個() throws IOException, LispException {
        ArrayList<String> stdList = new ArrayList<>();
        stdList.add("(define x 1)");
        stdList.add("x");

        Environment env = SetUp.getGlobalEnvironment();
        SExpression actual = null;

        for (String stdin : stdList) {
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);
        }

        SExpression expected = Int.valueOf(1);
        assertThat(actual, is(expected));
    }

    @Test(expected = LispException.class)
    public void 引数が3個() throws IOException, LispException {
        try {
            String stdin = "(define x 1 2)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("特殊形式defineの引数は2個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void 変数に手続きを束縛する1() throws IOException, LispException {
        ArrayList<String> stdList = new ArrayList<>();
        stdList.add("(define add (lambda (x y) (+ x y)))");
        stdList.add("(add 1 2)");

        Environment env = SetUp.getGlobalEnvironment();
        SExpression actual = null;

        for (String stdin : stdList) {
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);
        }

        SExpression expected = Int.valueOf(3);
        assertThat(actual, is(expected));
    }

    @Test
    public void 変数に手続きを束縛する2() throws IOException, LispException {
        ArrayList<String> stdList = new ArrayList<>();
        stdList.add("(define (add x y) (+ x y))");
        stdList.add("(add 1 2)");

        Environment env = SetUp.getGlobalEnvironment();
        SExpression actual = null;

        for (String stdin : stdList) {
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);
        }

        SExpression expected = Int.valueOf(3);
        assertThat(actual, is(expected));
    }

    @Test
    public void 変数に手続きを束縛する3() throws IOException, LispException {
        ArrayList<String> stdList = new ArrayList<>();
        stdList.add("(define (get-x1 x1 x2 . z) x1)");
        stdList.add("(define (get-x2 x1 x2 . z) x2)");
        stdList.add("(define (get-z x1 x2 . z) z)");
        stdList.add("(get-x1 1 2 3)");
        stdList.add("(get-x2 1 2 3)");
        stdList.add("(get-z 1 2 3)");

        Environment env = SetUp.getGlobalEnvironment();
        SExpression actual = null;

        for (String stdin : stdList) {
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);
        }

        SExpression expected = ConsCell.getInstance(Int.valueOf(3), EmptyList.getInstance());
        assertThat(actual, is(expected));
    }

    @Test
    public void 変数に手続きを束縛する4() throws IOException, LispException {
        ArrayList<String> stdList = new ArrayList<>();
        stdList.add("(define add (lambda () (+ 1 2)))");
        stdList.add("(add)");

        Environment env = SetUp.getGlobalEnvironment();
        SExpression actual = null;

        for (String stdin : stdList) {
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);
        }

        SExpression expected = Int.valueOf(3);
        assertThat(actual, is(expected));
    }

    @Test
    public void 変数に手続きを束縛する5() throws IOException, LispException {
        ArrayList<String> stdList = new ArrayList<>();
        stdList.add("(define (add) (+ 1 2)))");
        stdList.add("(add)");

        Environment env = SetUp.getGlobalEnvironment();
        SExpression actual = null;

        for (String stdin : stdList) {
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);
        }

        SExpression expected = Int.valueOf(3);
        assertThat(actual, is(expected));
    }

    @Test
    public void lambdaによる局所変数() throws IOException, LispException {
        ArrayList<String> stdList = new ArrayList<>();
        stdList.add("(define (f x) ((lambda (p q) (+ x p q)) 10 20))");
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
    public void lambdaの実引数が複数1() throws IOException, LispException {
        ArrayList<String> stdList = new ArrayList<>();
        stdList.add("(define add (lambda (x y) (+ x y) (* x y)))");
        stdList.add("(add 2 3)");

        Environment env = SetUp.getGlobalEnvironment();
        SExpression actual = null;

        for (String stdin : stdList) {
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);
        }

        SExpression expected = Int.valueOf(6);
        assertThat(actual, is(expected));
    }

    @Test
    public void lambdaの実引数が複数2() throws IOException, LispException {
        ArrayList<String> stdList = new ArrayList<>();
        stdList.add("(define add (lambda (x) (+ x x) (* x x)))");
        stdList.add("(add 4)");

        Environment env = SetUp.getGlobalEnvironment();
        SExpression actual = null;

        for (String stdin : stdList) {
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);
        }

        SExpression expected = Int.valueOf(16);
        assertThat(actual, is(expected));
    }


}
