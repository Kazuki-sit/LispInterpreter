package lisp.eval.graphics;

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

public class TestGraphicsDrawLine {
    Reader createReader(String stdin) {
        StringReader sr = new StringReader(stdin);
        BufferedReader br = new BufferedReader(sr);
        return new Reader(br);
    }

    @Test
    public void draw_lineの束縛() throws IOException, LispException {
        String stdin = "draw-line";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);
    }

    @Test
    public void 直線を描画する() throws IOException, LispException {
        ArrayList<String> stdList = new ArrayList<>();
        stdList.add("(define window (display test 800 800))");
        stdList.add("(draw-line window 10 10 500 500)");

        Environment env = SetUp.getGlobalEnvironment();

        for (String stdin : stdList) {
            Reader reader = createReader(stdin);
            SExpression sexp = reader.read();

            SExpression actual = Evaluator.eval(sexp, env);

            System.out.println("lisp> " + stdin);
            System.out.println(actual);
        }
    }
}
