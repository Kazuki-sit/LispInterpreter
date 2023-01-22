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

public class TestMod {
    Reader createReader(String stdin) {
        StringReader sr = new StringReader(stdin);
        BufferedReader br = new BufferedReader(sr);
        return new Reader(br);
    }

    @Test
    public void moduloの束縛() throws IOException, LispException {
        String stdin = "modulo";

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
            String stdin = "(modulo)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

            SExpression expected = Int.valueOf(0);
            assertThat(actual, is(expected));

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続きmoduloの引数は2個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test(expected = LispException.class)
    public void 引数が1個() throws IOException, LispException {
        try {
            String stdin = "(modulo 1)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

            SExpression expected = Int.valueOf(0);
            assertThat(actual, is(expected));

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続きmoduloの引数は2個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void 引数が2個() throws IOException, LispException {
        String stdin = "(modulo 1 2)";

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
            String stdin = "(modulo 1 2 3)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

            SExpression expected = Int.valueOf(0);
            assertThat(actual, is(expected));

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続きmoduloの引数は2個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test(expected = LispException.class)
    public void 引数に浮動小数点が含まれている() throws IOException, LispException {
        try {
            String stdin = "(modulo 6.0 3)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

            SExpression expected = Int.valueOf(0);
            assertThat(actual, is(expected));

        } catch (LispException e) {
            assertThat(e.getMessage(), is("手続きmoduloの引数はInt型である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }
}