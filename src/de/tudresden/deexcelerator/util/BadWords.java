package de.tudresden.deexcelerator.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * <span class="de">Schlechte W&oumlrter die nicht in Tabellen stehen sollen.</span>
 * <span class="en">Bad word, which should not stand in tables.</span>
 * @author Julian
 *
 */
public class BadWords {

	/**
	 * <span class="de">Set mit Schlechten W&oumlrtern.</span>
	 * <span class="en">Set with bad words.</span>
	 */
	public static final Set<String> badWords = new HashSet<String>(Arrays.asList(new String[] {
			"anus",
			"anal",
			"ass",
			"bitch",
			"bitches",
			"blow job",
			"blowjob",
			"boobs",
			"butt",
			"clit",
			"clits",
			"cock",
			"cocks",
			"cocksucking",
			"cum",
			"cunt",
			"cunts",
			"dick",
			"dildo",
			"dominatrix",
			"fag",
			"faggot",
			"fags",
			"fuck",
			"fucking",
			"gay",
			"gays",
			"hot",
			"jackoff",
			"jiss",
			"jizz",
			"lesbian",
			"lesbians",
			"masochist",
			"masturbate",
			"orgasm",
			"penis",
			"porn",
			"pussies",
			"pussy",
			"semen",
			"sex",
			"shemale",
			"shit",
			"slut",
			"sluts",
			"slutty",
			"suck",
			"sucking",
			"teets",
			"teen",
			"tit",
			"tits",
			"vagina",
			"whore",
			"xrated",
			"xxx",
			"xxx",
	}));

	/**
	 * <span class="de">Z&aumlhlen der vorkommenden Schlechten W&oumlrter in dem String.</span>
	 * <span class="en">Count the bad words in the input string.</span>
	 * @param s (<span class="de">String zum &uumlberpr&uumlfen</span>
	 * <span class="en">string to proof</span>)
	 * @return <span class="de">Anzahl vorkommender schlechter W&oumlrter</span>
	 * <span class="en">number of bad words in the string</span>
	 */
	public static int count(String s) {
		int count = 0;
		StringTokenizer st = new StringTokenizer(s);
		while (st.hasMoreTokens()) {
        	if (badWords.contains(st.nextToken().toLowerCase()))
        		count += 1;
     	}
        return count;
	}

}
