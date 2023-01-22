package lisp.eval.graphics;

import lisp.eval.*;
import lisp.eval.Float;
import lisp.exception.LispException;

import java.util.ArrayList;

public class GraphicsDrawTurtle implements Subroutine {

    /**
     * <p>渡されたフレームに亀を描画する</p>
     * <p>(draw-turtle display direction x y)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return 処理を行うフレーム
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if (sexpList.size() != 4) {
            throw new LispException("手続きdraw-turtleの引数は4個である必要があります");
        }

        SExpression display = sexpList.get(0);
        sexpList.remove(0);

        if (!(display instanceof Display)) {
            throw new LispException("手続きdraw-turtleの第一引数はDisplayである必要があります");
        }

        for(int i = 0;i<sexpList.size();i++) {
            if (sexpList.get(i) instanceof Int){
                sexpList.set(i, Float.valueOf((float) (((Int) sexpList.get(i)).getValue())));
                continue;
            }

            if (sexpList.get(i) instanceof Float){
                //sexpList.set(i, Int.valueOf(Math.round(((Float) sexpList.get(i)).getValue())));
                continue;
            }

            throw new LispException("手続きdraw-turtleの引数は数値である必要があります");
        }

        double d = ((Float)sexpList.get(0)).getValue();
        double x = ((Float)sexpList.get(1)).getValue();
        double y = ((Float)sexpList.get(2)).getValue();

        ((Display) display).getFrame().getPanel().drawTurtle(d, x, y);
        return display;
    }

    @Override
    public String toString() {
        return "#<subr draw-turtle>";
    }

}