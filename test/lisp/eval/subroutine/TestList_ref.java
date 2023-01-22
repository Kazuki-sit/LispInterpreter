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

public class TestList_ref {
    Reader createReader(String stdin) {
        StringReader sr = new StringReader(stdin);
        BufferedReader br = new BufferedReader(sr);
        return new Reader(br);
    }

    @Test
    public void list_refの束縛() throws IOException, LispException {
        String stdin = "list-ref";

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
            String stdin = "(list-ref)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

            SExpression expected = Int.valueOf(0);
            assertThat(actual, is(expected));

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続きlist-refの引数は2個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test(expected = LispException.class)
    public void 引数が1個() throws IOException, LispException {
        try {
            String stdin = "(list-ref '(1 2 3))";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

            SExpression expected = Int.valueOf(0);
            assertThat(actual, is(expected));

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続きlist-refの引数は2個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void 引数が2個() throws IOException, LispException {
        String stdin = "(list-ref '(1 2 3) 0)";

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
    public void 引数が3個() throws IOException, LispException {
        try {
            String stdin = "(list-ref '(1 2 3) 1 2)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

            SExpression expected = Int.valueOf(0);
            assertThat(actual, is(expected));

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続きlist-refの引数は2個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test(expected = LispException.class)
    public void 要素数の引数が数値ではない() throws IOException, LispException {
        try {
            String stdin = "(list-ref '(1 2 3) 'a)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

            SExpression expected = Int.valueOf(0);
            assertThat(actual, is(expected));

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続きlist-refの要素数の引数はInt型である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test(expected = LispException.class)
    public void 要素数の引数が範囲外() throws IOException, LispException {
        try {
            String stdin = "(list-ref '(1 2 3) 4)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

            SExpression expected = Int.valueOf(0);
            assertThat(actual, is(expected));

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続きlist-refの要素数の引数はリストの範囲内である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void リストのリスト() throws IOException, LispException {
        String stdin = "(list-ref '((x . 50) (y . 30) (z . 10)) 0)";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);

        SExpression expected = ConsCell.getInstance(Symbol.getInstance("x"), Int.valueOf(50));
        assertThat(actual, is(expected));
    }
}
