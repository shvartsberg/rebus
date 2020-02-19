package org.shvarz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model {
    String unparsedString;

    List<Letter> allLetters = new ArrayList<>();

    public Model(String stringRepresentation) {
        this.unparsedString = stringRepresentation;
        parse();
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    protected List<Term> terms = new ArrayList<>();
    protected Term sum;

    public Term getSum() {
        return sum;
    }

    public void setSum(Term sum) {
        this.sum = sum;
    }




    private void parse() {
        String[] args = unparsedString.split("=");
        String sumWord = args[1];
        sum = parse(sumWord);
        String[] termsWords = args[0].split("[+]");
        Arrays.stream(termsWords).forEach(w -> {
            Term argTerm = parse(w);
            terms.add(argTerm);
        });
    }

    Term parse(String word) {
        Term t = new Term();
        for (char c : word.toCharArray()) {
            Letter l = new Letter(c);
            if (!allLetters.contains(l)) {
                allLetters.add(l);
            } else {
                l = allLetters.get(allLetters.indexOf(l));
            }
            t.letters.add(l);
        }
        return t;
    }

    protected void  solve(int idx) {
        for (int i = 0; i < 10; i++) {
            // test already set
            boolean alreadySet = false;
            for (int a = 0; a < idx; a++) {
                if (allLetters.get(a).getValue() == i) {
                   alreadySet = true;
                   break;
                }
            }
            if (alreadySet) continue;

            allLetters.get(idx).setValue(i);

            if (idx == allLetters.size() - 1) {
                // test
                long summ = 0;
                for (Term t : terms) {
                    summ += t.value();
                }
                if (summ == sum.value()) {
                    System.out.println();
                    for (Term t : terms) {
                        System.out.print(t.value());
                        if (terms.indexOf(t) != terms.size() - 1) System.out.print("+");
                    }
                    System.out.print("=");
                    System.out.print(sum.value());
                }
            } else {
                solve(idx+1);
            }

        }
    }
}

class Letter {
    protected char letter;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    protected int value;

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public Letter(char c) {
        letter = c;
    }

    @Override
    public int hashCode() {
        return letter;
    }

    @Override
    public boolean equals(Object obj) {
        return letter == ((Letter)obj).letter;
    }
}

class Term {
    public List<Letter> letters = new ArrayList();

    public long value() {
        long result = 0;
        for (int i = 0; i < letters.size(); i++) {
            result = result * 10 + letters.get(i).getValue();
        }

        return result;
    }
}


