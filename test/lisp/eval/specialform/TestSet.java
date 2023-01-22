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

public class TestSet {
    Reader createReader(String stdin) {
        StringReader sr = new StringReader(stdin);
        BufferedReader br = new BufferedReader(sr);
        return new Reader(br);
    }

    @Test
    public void setの束縛() throws IOException, LispException {
        String stdin = "set!";

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
            String stdin = "(set!)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("特殊形式set!の引数は2個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test(expected = LispException.class)
    public void 引数が1個() throws IOException, LispException {
        try {
            String stdin = "(set! x)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("特殊形式set!の引数は2個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void 引数が2個() throws IOException, LispException {
        ArrayList<String> stdList = new ArrayList<>();
        stdList.add("(define x 1)");
        stdList.add("x");
        stdList.add("(set! x 2)");
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

        SExpression expected = Int.valueOf(2);
        assertThat(actual, is(expected));
    }

    @Test(expected = LispException.class)
    public void 引数が3個() throws IOException, LispException {
        try {
            String stdin = "(set! x 1 2)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("特殊形式set!の引数は2個である必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test(expected = LispException.class)
    public void 第一引数がシンボルではない() throws IOException, LispException {
        try {
            String stdin = "(set! 1 2)";

            Environment env = SetUp.getGlobalEnvironment();
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);

        } catch (LispException e) {
            assertThat(e.getMessage(), is("特殊形式set!の第一引数はシンボルである必要があります"));
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
