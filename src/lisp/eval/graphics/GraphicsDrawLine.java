package lisp.eval.graphics;

import lisp.eval.*;
import lisp.eval.Float;
import lisp.exception.LispException;

import java.util.ArrayList;

public class GraphicsDrawLine implements Subroutine {

    /**
     * <p>渡されたフレームに直線を描画する</p>
     * <p>(draw-line display x1 y2 x2 y2)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return 処理を行うフレーム
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if (sexpList.size() != 5) {
            throw new LispException("手続きdraw-lineの引数は5個である必要があります");
        }

        SExpression display = sexpList.get(0);
        sexpList.remove(0);

        if (!(display instanceof Display)) {
            throw new LispException("手続きdraw-lineの第一引数はDisplayである必要があります");
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

            throw new LispException("手続きdraw-lineの引数は数値である必要があります");
        }

        double x1 = ((Float) (sexpList.get(0))).getValue();
        double y1 = ((Float) (sexpList.get(1))).getValue();
        double x2 = ((Float) (sexpList.get(2))).getValue();
        double y2 = ((Float) (sexpList.get(3))).getValue();

        ((Display) display).getFrame().getPanel().drawLine(x1, y1, x2, y2);
        return display;
    }

    @Override
    public String toString() {
        return "#<subr draw-line>";
    }
}
