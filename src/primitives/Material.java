package primitives;

public class Material {


    public Double3 getkD() {
        return kD;
    }

    public Double3 getkS() {
        return kS;
    }

    public int getnShininess() {
        return nShininess;
    }

    public Double3 getkR() {
        return kR;
    }

    /**
     * factors
     */
    public Double3 kD = Double3.ZERO;
    public Double3 kS = Double3.ZERO;
    public int nShininess = 0;



    public Double3 kT = Double3.ZERO;
    public Double3 getkT() {
        return kT;
    }
    public Double3 kR = Double3.ZERO;

    /**
     * set with Double3
     *
     * @param kD
     * @return this
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * set with Double3
     *
     * @param kS
     * @return this
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * set with Double
     *
     * @param kD
     * @return this
     */
    public Material setkD(Double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * set with Double
     *
     * @param kS
     * @return this
     */
    public Material setkS(Double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    public Material setkT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    public Material setkR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * set nShininess
     *
     * @param nShininess
     * @return
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }


}
