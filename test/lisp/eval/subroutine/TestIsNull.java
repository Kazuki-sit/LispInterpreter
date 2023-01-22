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


public class TestIsNull {
    Reader createReader(String stdin) {
        StringReader sr = new StringReader(stdin);
        BufferedReader br = new BufferedReader(sr);
        return new Reader(br);
    }

    @Test
    public void isNullの束縛() throws IOException, LispException {
        String stdin = "null?";

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
            String stdin = "(null?)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続きnull?の引数は1個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void 引数が1個() throws IOException, LispException {
        String stdin = "(null? ())";

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
    public void 引数が2個() throws IOException, LispException {
        try {
            String stdin = "(null? () 'x)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続きnull?の引数は1個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void 空リスト以外1() throws IOException, LispException {
        String stdin = "(null? 1)";

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
    public void 空リスト以外2() throws IOException, LispException {
        String stdin = "(null? #t)";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);

        SExpression expected = Bool.valueOf(false);
        assertThat(actual, is(expected));
    }
}
