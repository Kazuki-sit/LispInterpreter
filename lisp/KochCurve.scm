(define window (display koch-curve 1400 1000))
(define Pi 3.1415926)
(define x 300)
(define y 500)
(define d 0)
(define (forward distance)
    (let
        (
            (nx (+ x (* distance (cos (/ (* Pi d) 180)))))
            (ny (+ y (* distance (sin (/ (* Pi d) 180)))))
        )
        (draw-line window x y nx ny)
        (draw-turtle window (/ (* Pi (+ d 90)) 180) nx ny)
        (sleep 50)
        (set! x nx)
        (set! y ny)
    )
 )
(define (right angle) (set! d (+ d angle)))
(define (left angle) (set! d (- d angle)))
(define (turtle-koch width n)
    (if (= n 0)
        (forward width)
        (begin
            (turtle-koch (/ width 3.0) (- n 1))
            (left 60)
            (turtle-koch (/ width 3.0) (- n 1))
            (right 120)
            (turtle-koch (/ width 3.0) (- n 1))
            (left 60)
            (turtle-koch (/ width 3.0) (- n 1))
        )
    )
 )
(turtle-koch 800 4)