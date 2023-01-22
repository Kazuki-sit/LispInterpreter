package lisp.eval.graphics;

import lisp.eval.Int;
import lisp.eval.SExpression;
import lisp.eval.Symbol;

public class Display implements SExpression {

    // 描画するウィンドウ
    private GraphicsFrame frame;

    /**
     * <p>フレームを保持するS式</p>
     *
     * @param name ウィンドウのタイトル
     * @param width 画面の幅
     * @param height 画面の高さ
     */
    private Display(Symbol name, Int width, Int height) {
        this.frame = new GraphicsFrame(name.getName(), width.getValue(), height.getValue());
        this.frame.setVisible(true);
    }

    /**
     * <p>Displayのインスタンスを得る</p>
     *
     * @param name ウィンドウのタイトル
     * @param width 画面の幅
     * @param height 画面の高さ
     * @return インスタンス
     */
    public static SExpression getInstance(Symbol name, Int width, Int height) {
        return new Display(name, width, height);
    }

    /**
     * <p>保持しているJFrameを返す</p>
     *
     * @return JFrame
     */
    public GraphicsFrame getFrame() {
        return this.frame;
    }

    @Override
    public String toString() {
        return "#<frame>";
    }
}
