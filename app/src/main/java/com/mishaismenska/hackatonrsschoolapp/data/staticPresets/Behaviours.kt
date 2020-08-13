package com.mishaismenska.hackatonrsschoolapp.data.staticPresets

enum class Behaviours(val lowestConcentration: Double) {
    SOBER(0.0),
    ALMOST_NORMAL(0.2),
    EUPHORIC(0.3),
    DISINHIBITIONS(0.6),
    EXPRESSIVENESS(1.0),
    STUPOR(2.0),
    UNCONSCIOUS(3.0),
    BLACKOUT(4.0),
    DEAD(5.0)
}
