package primitives;

public class Material {


    /**
     * factors
     */
    public Double3 kD = new Double3(0);
    public Double3 kS= new Double3(0);
    public int nShininess=0;

    /**
     * set with Double3
     * @param kD
     * @return this
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * set with Double3
     * @param kS
     * @return this
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * set with Double
     * @param kD
     * @return this
     */
    public Material setkD(Double kD) {
        this.kD= new Double3(kD);
        return this;
    }
    /**
     * set with Double
     * @param kS
     * @return this
     */
    public Material setkS(Double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * set nShininess
     * @param nShininess
     * @return
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }



}
