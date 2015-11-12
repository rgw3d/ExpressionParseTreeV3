import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 * All the math operations used in each Simplifier.Operator class
 * Created by rgw3d on 10/10/2014.
 */
public class MathOperations {

    /**
     * This method will do addition in a list that has two terms
     * @param terms all Simplifier.ExpressionNode objects to add.  size() must == 2
     */
    public static void additionControl(ArrayList<ExpressionNode> terms){
        if(terms.size()!=2)
            throw new IndexOutOfBoundsException("Terms had a size that was not 2: " +terms.size());
        ArrayList<ExpressionNode> allTerms = terms.get(0).evaluate();
        allTerms.addAll(terms.get(1).evaluate());
        if(sameVariablePower(allTerms)){
            terms.clear();
            terms.add(variableIndependentAddition(allTerms));
        }
        else{
            ArrayList<ExpressionNode> complexAddition = complexAdditionControl(allTerms);
            terms.clear();//add the results back to the same list, so we clear the old list
            terms.addAll(complexAddition);

        }
    }

    /**
     * Used to control the complex addition (fractions and things with different variables)
     * this is a method because it is used after the multiplication method
     * @param terms list of all terms to be added
     * @return simplified list of all terms that were added
     */
    private static ArrayList<ExpressionNode> complexAdditionControl(ArrayList<ExpressionNode> terms) {
        ArrayList<ExpressionNode> nominalsOnly = new ArrayList<ExpressionNode>();
        ArrayList<ExpressionNode> others = new ArrayList<ExpressionNode>();


        for(ExpressionNode node: terms){
            if(node instanceof NumberStructure.Number)
                nominalsOnly.add(node);
            else
                others.add(node);
        }

        if(nominalsOnly.size()>1)
            variableDependentAddition(nominalsOnly);
        if(others.size()>1)
          mixedAddition(others);

        terms.clear();
        terms.addAll(nominalsOnly);
        terms.addAll(others);

        return terms;
    }

    /**
     * Checks to see if the variables of a list of Simplifier.ExpressionNode objects have the same getVariable() value.
     * this would mean that the terms are easy to add together, and simply summing the getCoefficient() values is sufficient
     * @param terms all Simplifier.ExpressionNode objects to test
     * @return boolean if getVariable() is the same in all terms objects.  if any term is not instanceOf Simplifier.Number, returns false
     */
    private static boolean sameVariablePower(ArrayList<ExpressionNode> terms){
        double variablePower = terms.get(0).getVariable();
        for(ExpressionNode node: terms){
            if(!(node instanceof NumberStructure.Number) || node.getVariable()!=variablePower)
                return false;
        }
        return true;
    }

    /**
     * Sums the getCoefficient() value of a list of terms.
     * Assumes sameVariablePower()==true;
     * @param terms all Simplifier.ExpressionNode objects to add
     * @return Simplifier.Number that contains summed value along with correct getVariable() value.
     */
    private static NumberStructure.Number variableIndependentAddition(ArrayList<ExpressionNode> terms){
        double total = 0;
        for(ExpressionNode node: terms)
            total+=node.getCoefficient();
        return new NumberStructure.Number(total,terms.get(0).getVariable());
    }

    /**
     * Run if sameVariablePower()==false;
     * Adds numbers if their variable exponents allow them to.
     * @param terms all Simplifier.ExpressionNode objects to add
     */
    private static void variableDependentAddition(ArrayList<ExpressionNode> terms) {
        Hashtable<Double, ArrayList<ExpressionNode>> sortedNominals = new Hashtable<Double, ArrayList<ExpressionNode>>();

        for (ExpressionNode nom : terms) {//add everyone to their respective groups
            Double nomBottom = nom.getVariable();//the variable value of the nominal
            try {
                sortedNominals.get(nomBottom).add(nom);//use the key to get the ArrayList<Simplifier.ExpressionNode> and then add the nominal
            } catch (NullPointerException E) {//key was not mapped to a value
                ArrayList<ExpressionNode> tmp = new ArrayList<ExpressionNode>();
                tmp.add(nom);
                sortedNominals.put(nomBottom, tmp);
            }
        }

        terms.clear();//clear the original list so that we can add on to it

        ArrayList<Double> varsAdded = new ArrayList<Double>();
        varsAdded.addAll(sortedNominals.keySet());
        Collections.sort(varsAdded);
        Collections.reverse(varsAdded);//descending order
        for (Double var : varsAdded) {
            NumberStructure.Number simp = variableIndependentAddition(sortedNominals.get(var));
            if(simp.getCoefficient()!=0)//num doesn't equal zero.  stops this situation--> 0.0x^2.0 from happening and screwing things up
                terms.add(simp);
        }
    }

    /**
     * This is run if there are things other than Simplifier.Number type objects to add together
     * @param terms all Simplifier.ExpressionNode objects to add
     */
    private static void mixedAddition(ArrayList<ExpressionNode> terms) {
        ArrayList<ExpressionNode> fractions = new ArrayList<ExpressionNode>();
        ArrayList<ExpressionNode> others = new ArrayList<ExpressionNode>();//anything that is not a fraction

        for(ExpressionNode node: terms){
            if(node instanceof NumberStructure.Fraction)
                fractions.add(node);
            else
                others.add(node);
        }

        if(fractions.size()>1)
            fractionAddition(fractions);

        terms.clear();
        terms.addAll(fractions);
        terms.addAll(others);
    }

    /**
     * run if there are fractions to add together.
     * Will add fractions together if their bottoms are the same.  (common denominator)
     * @param terms all Simplifier.ExpressionNode objects to add
     */
    private static void fractionAddition(ArrayList<ExpressionNode> terms) {
        Hashtable<ArrayList<ExpressionNode>, ArrayList<ExpressionNode>> sortedFractions = new Hashtable<ArrayList<ExpressionNode>, ArrayList<ExpressionNode>>();

        for (ExpressionNode node : terms) {
            try {
                sortedFractions.get(node.getBottom()).add(node);//from the node.getBottom() arrayList key, add to it the fraction
            } catch (NullPointerException E) {//key was not mapped to a value
                ArrayList<ExpressionNode> tmp = new ArrayList<ExpressionNode>();
                tmp.add(node);
                sortedFractions.put(node.getBottom(), tmp);
            }

        }

        terms.clear();
        for (ArrayList<ExpressionNode> x : sortedFractions.keySet()) {
            ArrayList<ExpressionNode> addTop = new ArrayList<ExpressionNode>();
            for(ExpressionNode fraction: sortedFractions.get(x))
                addTop.addAll(fraction.getTop());

            variableDependentAddition(addTop);

            if(x.size()!=1 || x.get(0).getCoefficient()!=0)
                terms.add(simplifyFractions(new NumberStructure.Fraction(addTop, x)));

        }
    }


    /**
     * Multiplication Control.  This starts the multiplication simplification process
     * @param terms all Simplifier.ExpressionNode objects to multiply.  size() must == 2
     *
     */
    public static void multiplicationControl(ArrayList<ExpressionNode> terms){
        if(terms.size()!=2)
            throw new IndexOutOfBoundsException("Terms had a size that was not 2: " +terms.size());

        if(onlyNumberStructures(terms)){
            NumberStructure product = nominalMultiplication(terms);
            terms.clear();
            terms.add(product);
        }
        else {
            ArrayList<ExpressionNode> products = multiplyLists(terms.get(0).evaluate(), terms.get(1).evaluate());
            terms.clear();
            terms.addAll(complexAdditionControl(products));
        }
    }

    /**
     * Multiplies two NumberStructures together.  treats them as fractions regardless of type, and simplifies after the multiplication
     * @param terms all Simplifier.NumberStructure objects to multiply.  Must be instanceOf Simplifier.NumberStructure
     * @return Simplifier.NumberStructure, either a fraction or nominal
     */
    private static NumberStructure nominalMultiplication(ArrayList<ExpressionNode> terms) {
        NumberStructure.Fraction fraction = new NumberStructure.Fraction(NumberStructure.Number.One);
        for(ExpressionNode node : terms){
            ArrayList<ExpressionNode> tmpTop = multiplyLists(fraction.getTop(), node.getTop());
            fraction.getTop().clear();
            fraction.getTop().addAll(tmpTop);

            ArrayList<ExpressionNode> tmpBot = multiplyLists(fraction.getBottom(), node.getBottom());
            fraction.getBottom().clear();
            fraction.getBottom().addAll(tmpBot);
        }
        if(fraction.getTop().size()==1 && fraction.getBottom().size()==1 && fraction.getBottom().get(0).equals(NumberStructure.Number.One))
            return new NumberStructure.Number(fraction.getTop().get(0).getCoefficient(), fraction.getTop().get(0).getVariable());
        else
            return simplifyFractions(fraction);

    }

    /**
     * Multiplies two arrayLists together.  Left * Right = product
     * left and right could be arrayLists of any amount of EquationNodes
     * @param left the left arrayList (or parenthesis)
     * @param right the right arrayList
     * @return the multiplied product list
     */
    private static ArrayList<ExpressionNode> multiplyLists(ArrayList<ExpressionNode> left, ArrayList<ExpressionNode> right) {
        ArrayList<ExpressionNode> result = new ArrayList<ExpressionNode>();

        for(ExpressionNode leftTerm : left){
            for(ExpressionNode rightTerm : right){
                if(leftTerm instanceof NumberStructure.Number && rightTerm instanceof NumberStructure.Number)
                    result.add(new NumberStructure.Number(leftTerm.getCoefficient()*rightTerm.getCoefficient(),leftTerm.getVariable() + rightTerm.getVariable()));
                else if( leftTerm instanceof NumberStructure && rightTerm instanceof NumberStructure) {
                    ArrayList<ExpressionNode> leftAndRightNodes = new ArrayList<ExpressionNode>();
                    leftAndRightNodes.add(leftTerm);
                    leftAndRightNodes.add(rightTerm);
                    result.add(nominalMultiplication(leftAndRightNodes));
                }
                else if( leftTerm instanceof Operator || rightTerm instanceof Operator){
                    ArrayList<ExpressionNode> tmpList = new ArrayList<ExpressionNode>();
                    tmpList.add(leftTerm);
                    tmpList.add(rightTerm);
                    result.add(new MultiplicationOperator(tmpList));
                }
            }
        }
        return result;
    }

    /**
     * Used to determine the path of the multiplication operation.
     * @param terms all Simplifier.ExpressionNode objects to test
     * @return boolean true if terms consists of just NumberStructures (fractions or Nominals)
     */
    private static boolean onlyNumberStructures(ArrayList<ExpressionNode> terms) {
        int numberStructures = 0;

        for(ExpressionNode node : terms){
            if(node instanceof NumberStructure)
                numberStructures++;
        }

        return numberStructures == 2;
    }

    /**
     * Starts the division process.  This returns a fraction or a nominal, depending on what can be divided
     * @param terms all Simplifier.ExpressionNode objects to divide.  size() must == 2
     */
    public static void divisionControl(ArrayList<ExpressionNode> terms) {
        if(terms.size()!=2)
            throw new IndexOutOfBoundsException("Terms had a size that was not 2: " +terms.size());
        NumberStructure fraction = new NumberStructure.Fraction(terms.get(0).evaluate(), terms.get(1).evaluate());
        fraction = simplifyFractions(fraction);
        terms.clear();
        terms.add(fraction);

    }


    /**
     * This function will simplify fractions.  it will take (3x/3) and return x  (new Simplifier.Number(1,1))
     * @param fraction the Simplifier.NumberStructure (a Simplifier.Number will just be returned) to be simplified
     * @return NumberStructure. the simplified fraction, or can be simplified into a nominal.
     */
    private static NumberStructure simplifyFractions(NumberStructure fraction) {
        if(fraction instanceof NumberStructure.Number)//if a nominal is sent. cannot be simplified
            return fraction;

        for (ExpressionNode top : fraction.getTop()) {//simplify underlying fractions
            if (top instanceof NumberStructure.Fraction) {//could be a fraction in a fraction
                int tmpIndx = fraction.getTop().indexOf(top);//get indx of fraction
                fraction.getTop().remove(top);//remove the object from the list
                fraction.getTop().add(tmpIndx, simplifyFractions((NumberStructure.Fraction) top));//add the "simplified" object back in
            }
        }
        for (ExpressionNode bot : fraction.getBottom()) {//simplify underlying fractions
            if (bot instanceof NumberStructure.Fraction) {//could be a fraction in a fraction
                int tmpIndx = fraction.getBottom().indexOf(bot);//get indx of fraction
                fraction.getBottom().remove(bot);//remove the object from the list
                fraction.getBottom().add(tmpIndx, simplifyFractions((NumberStructure.Fraction) bot));//add the "simplified" object back in
            }
        }

        ArrayList<Integer> topDivisors = new ArrayList<Integer>();
        ArrayList<Integer> botDivisors = new ArrayList<Integer>();
        boolean canDivide = findListGCD(fraction.getTop(), topDivisors) && findListGCD(fraction.getBottom(), botDivisors);

        if (canDivide) {//this test ensures that there are no fractions
            simplifyFractionByGCD(fraction, topDivisors, botDivisors);//reduce the fraction

            //bellow reduces variables
            double topSmallestVar = findSmallestVariableExponent(fraction.getTop());
            double botSmallestVar = findSmallestVariableExponent(fraction.getBottom());

            if (topSmallestVar!=-1 && botSmallestVar!=-1) {
                reduceVariableExponents(fraction, topSmallestVar, botSmallestVar);
            }
        }

        if (fraction.getTop().size() == 1 && fraction.getBottom().size() == 1 && fraction.getBottom().get(0).equals(NumberStructure.Number.One)) //if the number does not equal zero
            return new NumberStructure.Number(fraction.getTop().get(0).getCoefficient(), fraction.getTop().get(0).getVariable());

        else if(fraction.getTop().size() == 1 && fraction.getBottom().size() == 1//if only numbers are in the top and bottom.
                && fraction.getTop().get(0).getVariable() == 0 && fraction.getTop().get(0) instanceof NumberStructure.Number
                && fraction.getBottom().get(0).getVariable() == 0 && fraction.getBottom().get(0) instanceof NumberStructure.Number)
            return new NumberStructure.Number(fraction.getTop().get(0).getCoefficient()/fraction.getBottom().get(0).getCoefficient(),0);//return the division of these numbers

        return fraction;


    }




    /**
     * Used to get the GCD list of factors for a list of NumberStructures. used by the simplifyFractions() method
     *
     * This section either loops through the top of the fraction, or the bottom of it determining the greatest
     * common divisor of all the numbers.
     * Determines that canDivide = false if there is fraction present in the top or bottom of the fraction.
     *      this also clears the list of Divisors
     * Loops through each value while looping though each value.
     *      get(0) is tested against get(0) through get(size()-1) to determine the GCD
     * @param list the list of NumberStructures to test for a list of GCDs
     * @param divisorList the list of GCDs to be added to.  This list will be cleared if the returned value of this function is false
     * @return boolean of if it was able to generate a list of GCDs
     */
    private static boolean findListGCD(ArrayList<ExpressionNode> list, ArrayList<Integer> divisorList) {
        boolean canDivide = true;
        outerLoop:
        for (ExpressionNode outside : list) {//these should all be nominals.  if not, clear list and break
            int possibleOutsideGCD = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof NumberStructure.Number) {
                    double outsideNum = outside.getCoefficient();
                    double insideNum = list.get(i).getCoefficient();

                    if ((int)outsideNum == outsideNum && (int)insideNum == insideNum) {//if both are integers
                        if (possibleOutsideGCD == 0) {
                            possibleOutsideGCD = GCD((int) outsideNum, (int) insideNum);
                        } else {
                            if (!((int) insideNum % possibleOutsideGCD == 0)) {//so the factor doesn't work
                                possibleOutsideGCD = GCD((int) outsideNum, (int) insideNum);
                                i = 0;//resetting the loop so that this new gcd value is tested.

                            }//if false, then its good -- do nothing because this factor works.
                        }
                    } else {
                        canDivide = false;
                        divisorList.clear();
                        break outerLoop;
                    }

                } else {
                    canDivide = false;
                    divisorList.clear();
                    break outerLoop;
                }
            }
            //this is where we add the possibleGCD value to the ArrayList if it has survived
            if (possibleOutsideGCD != 1 && possibleOutsideGCD != 0)
                divisorList.add(possibleOutsideGCD);
        }
        return canDivide;
    }

    /**
     * used to find the greatest common divisor (GCD)
     * @param a one of the factors
     * @param b one of the factors
     * @return GCD of a and b
     */
    private static int GCD(int a, int b) {//greatest common divisor
        if (b==0)
            return a;
        return GCD(b,a%b);
    }

    /**
     * Take the GCD of both the top and bottom of a fraction and see what simplification can be done
     * @param fraction the fraction to simplify
     * @param topDivisors list of the top
     * @param botDivisors list of the bottom
     */
    private static void simplifyFractionByGCD(NumberStructure fraction, ArrayList<Integer> topDivisors, ArrayList<Integer> botDivisors) {
        Collections.sort(topDivisors);
        Collections.reverse(topDivisors);//reverse because we start with the highest value.
        Collections.sort(botDivisors);
        Collections.reverse(botDivisors);

        botDivisorsLoop:
        for (Integer x : botDivisors) {
            for (Integer y : topDivisors) {
                if (y % x == 0) {//yay it actually works. this means that the greatest divisor has been found.
                    ArrayList<ExpressionNode> tmpTop = new ArrayList<ExpressionNode>();
                    ArrayList<ExpressionNode> tmpBot = new ArrayList<ExpressionNode>();

                    for (ExpressionNode node : fraction.getTop())
                        tmpTop.add(new NumberStructure.Number((node.getCoefficient() / x), node.getVariable()));

                    for (ExpressionNode node : fraction.getBottom())
                        tmpBot.add(new NumberStructure.Number((node.getCoefficient() / x), node.getVariable()));

                    fraction.getTop().clear();
                    fraction.getTop().addAll(tmpTop);

                    fraction.getBottom().clear();
                    fraction.getBottom().addAll(tmpBot);

                    break botDivisorsLoop;
                }
            }
        }
    }

    /**
     * Used to find the smallest variable exponent in a list of Nominals
     * Assumed that Fractions are not a part of this list
     * Assumed that there are no negative exponents - they are ignored
     * @param list list of Nominals! Assumed that fractions are not a part of this list!
     * @return the smallest variable exponent in the list
     */
    public static int findSmallestVariableExponent(ArrayList<ExpressionNode> list) {
        int smallestVar = -1;
        for (ExpressionNode x : list) {
            if (x instanceof NumberStructure.Number) {
                if (smallestVar == -1)
                    smallestVar = (int) x.getVariable();
                else if (smallestVar > x.getVariable() && x.getVariable() >= 0)
                    smallestVar = (int) x.getVariable();
            }
        }
        return smallestVar;
    }

    /**
     * This function reduces all of the Fractions Number 's variable exponent value by subtracting the lowest common variable
     *  exponent from all the Number s
     * Essentially, it reduces all the Nominals.
     * Assumed that the fraction.getTop() and getBottom() return a list of only Nominals! no Fractions within fractions
     * @param fraction the fraction that will be reduced
     * @param topSmallestVar the smallest var value common to all Nominals in the top of the fraction
     * @param botSmallestVar the smallest var value common to all Nominals in the bottom of the fraction
     */
    private static void reduceVariableExponents(NumberStructure fraction, double topSmallestVar, double botSmallestVar) {
        double reduceValue;
        if (topSmallestVar >= botSmallestVar)
            reduceValue = botSmallestVar;
        else
            reduceValue = topSmallestVar;

        ArrayList<ExpressionNode> tmpTop = new ArrayList<ExpressionNode>();
        ArrayList<ExpressionNode> tmpBot = new ArrayList<ExpressionNode>();

        for (ExpressionNode node : fraction.getTop())
            tmpTop.add(new NumberStructure.Number((node.getCoefficient()), node.getVariable() - reduceValue));
        for (ExpressionNode node : fraction.getBottom())
            tmpBot.add(new NumberStructure.Number((node.getCoefficient()), node.getVariable() - reduceValue));

        fraction.getTop().clear();
        fraction.getTop().addAll(tmpTop);

        fraction.getBottom().clear();
        fraction.getBottom().addAll(tmpBot);
    }



    /**
     * This starts the process of raising something to a power. terms.get(0) is the base.  terms.get(1) is the power
     * @param terms all Simplifier.ExpressionNode objects to be a part of this operation.  size() must == 2
     */
    public static void powerControl(ArrayList<ExpressionNode> terms){
        if(terms.size()!=2)
            throw new IndexOutOfBoundsException("Terms had a size that was not 2: " +terms.size());
        if(canRaiseToPower(terms)){//if exponent is or can be simplified to one integer.
            if (terms.get(0) instanceof NumberStructure.Number) {//meaning that the first term is actually something else. like a multiplication operator or something.
                baseIsNominal(terms);
            }
            else
            {
                baseIsOperator(terms);
            }


        }
        else{//special case. base is nominal.  exponent is real number without variables. but it could be something like 2.2 or 22/10
            if(terms.get(0) instanceof NumberStructure.Number
                    && terms.get(1).evaluate().size() == 1
                    && terms.get(1).evaluate().get(0) instanceof NumberStructure
                    && terms.get(1).evaluate().get(0).getTop().size() == 1
                    && terms.get(1).evaluate().get(0).getBottom().size() == 1
                    && terms.get(1).evaluate().get(0).getTop().get(0).getVar() == 0
                    && terms.get(1).evaluate().get(0).getBottom().get(0).getVar() == 0)
                baseIsNominal(terms);
            else {
            PowerOperator powerOperator = new PowerOperator(terms);
                terms.clear();
                terms.add(powerOperator);
            }

        }

    }

    /**
     * Raises the returned list of the operator (the base, terms.get(0)) to the power (terms.get(1).getCoefficient())
     * @param terms the base and the exponent to simplify
     */
    private static void baseIsOperator(ArrayList<ExpressionNode> terms) {
        int expnt = (int) terms.get(1).evaluate().get(0).getTop().get(0).getCoef();
        //get exponent.  will be cast to an int.
        if(expnt == 0) {
            terms.clear();
            terms.add(NumberStructure.Number.One);
        }
        else if(expnt <0){//negative exponent
            NumberStructure.Fraction flipped = new NumberStructure.Fraction(terms.get(0).evaluate());
            expnt = Math.abs(expnt);

            if(expnt-1==0){//not multiplied.  exponent of 1
                terms.clear();
                terms.add(flipped);

            }
            else {
                MultiplicationOperator[] raisedPowers = new MultiplicationOperator[expnt - 1];
                for (int i = raisedPowers.length - 1; i >= 0; i--) {
                    if (i == raisedPowers.length - 1) {
                        raisedPowers[i] = new MultiplicationOperator();
                        raisedPowers[i].addTerm(flipped);
                        raisedPowers[i].addTerm(flipped);
                    } else {
                        raisedPowers[i] = new MultiplicationOperator();
                        raisedPowers[i].addTerm(raisedPowers[i + 1]);
                        raisedPowers[i].addTerm(flipped);
                    }
                }
                terms.clear();
                terms.addAll(raisedPowers[0].evaluate());
            }

        }
        else
        {
            if(expnt-1==0){//not multiplied
                ArrayList<ExpressionNode> simplified = terms.get(0).evaluate();
                terms.clear();
                terms.addAll(simplified);
            }
            else {

                MultiplicationOperator[] raisedPowers = new MultiplicationOperator[expnt - 1];
                for (int i = raisedPowers.length - 1; i >= 0; i--) {
                    if (i == raisedPowers.length - 1) {
                        raisedPowers[i] = new MultiplicationOperator();
                        raisedPowers[i].addTerm(terms.get(0));
                        raisedPowers[i].addTerm(terms.get(0));
                    } else {
                        raisedPowers[i] = new MultiplicationOperator();
                        raisedPowers[i].addTerm(raisedPowers[i + 1]);
                        raisedPowers[i].addTerm(terms.get(0));
                    }
                }
                terms.clear();
                terms.addAll(raisedPowers[0].evaluate());
            }
        }
    }

    private static void baseIsNominal(ArrayList<ExpressionNode> terms) {
        if (terms.get(0).getVariable() == 0) {//meaning that there is no variable in the base
            NumberStructure.Number simplified = new NumberStructure.Number(Math.pow(terms.get(0).getCoefficient(),
                     terms.get(1).evaluate().get(0).getTop().get(0).getCoef()/terms.get(1).evaluate().get(0).getBottom().get(0).getCoef()), 0);
            terms.clear();
            terms.add(simplified);

        } else {//with a variable in the base
            NumberStructure.Number simplified = new NumberStructure.Number(terms.get(0).getCoefficient(),
                    terms.get(0).getVariable() * ( terms.get(1).evaluate().get(0).getTop().get(0).getCoef()/terms.get(1).evaluate().get(0).getBottom().get(0).getCoef()));
            terms.clear();
            terms.add(simplified);
        }
    }

    /**
     * This determines if there is a variable in the exponent and if the exponent is an int
     * if this is true, anything can be raised to this exponent
     * @return boolean true if there is no variable in exponent, if exponent is a nominal (or fraction like 3/1) and is an int.
     */
    private static boolean canRaiseToPower(ArrayList<ExpressionNode> terms){
        if (terms.get(1) instanceof NumberStructure.Number && terms.get(1).getVariable() == 0 && terms.get(1).getCoefficient()==(int)terms.get(1).getCoefficient()) {
            return true;
        } else {
            ArrayList<ExpressionNode> list = terms.get(1).evaluate();//get the list for the exponent
            if (list.size() == 1 && list.get(0) instanceof NumberStructure.Number && list.get(0).getVariable() == 0) {
                return true;//if it simplifies to a nominal without a variable
            } else if (list.size() == 1 && list.get(0) instanceof NumberStructure.Fraction) {
                NumberStructure simplifiedFraction = simplifyFractions((NumberStructure) list.get(0));
                return (simplifiedFraction.getTop().size() == 1 && simplifiedFraction.getTop().get(0) instanceof NumberStructure.Number && simplifiedFraction.getTop().get(0).getVariable() == 0 &&
                        simplifiedFraction.getBottom().size() == 1 && simplifiedFraction.getBottom().get(0).equals(NumberStructure.Number.One));
                //this returns true if the fraction is something like (3/1)
            }
            return false;
        }
    }

    /**
     * Count the number of fractions in a list of EquationNodes
     *
     * @param list the list of EquationNodes
     * @return count of the fractions in the list
     */
    public static int countFractions(ArrayList<ExpressionNode> list){
        int count = 0;
        for(ExpressionNode node: list){
            if(node instanceof NumberStructure.Fraction)
                count ++;
        }
        return count;
    }

    /**
     * Find the highest exponent of a variable, assuming no fractions, in a polynomial.
     * Fractions are ignored!
     *
     * @param list the list of EquationNodes
     * @return double of the highestExponent
     */
    public static double findHighestVariableExponent(ArrayList<ExpressionNode> list){
        double highestExponent = 0;
        for(ExpressionNode node: list){
            if(node instanceof NumberStructure.Number){
                if(node.getVariable()>highestExponent)
                    highestExponent = node.getVariable();
            }
        }
        return highestExponent;
    }

    /**
     * Count the number of Nominals with variable exponent values that are not zero
     *
     * @param list the list of EquationNodes
     * @return count of the Nominals with variable exponent values that are not zero
     */
    public static int countNominalsWithVars(ArrayList<ExpressionNode> list){
        int count = 0;
        for(ExpressionNode node: list){
            if(node instanceof NumberStructure.Number && node.getVariable()!=0)
                count ++;
        }
        return count;
    }

    /**
     * Finds the first instance of a specified degree Number in a list of Nominals
     * Assumes list only has Nominals
     * @param list list of Nominals to be tested
     * @param degree the specified variable degree to find
     * @return first instance of Nth degree Number, or Number.ONE if nothing of that degree is found
     */
    public static NumberStructure.Number findNthDegreeNominal(double degree,ArrayList<ExpressionNode> list ){
        for(ExpressionNode node: list){
            if(node.getVariable() == degree)
                return (NumberStructure.Number)node;
        }
        return NumberStructure.Number.Zero;
    }

}
