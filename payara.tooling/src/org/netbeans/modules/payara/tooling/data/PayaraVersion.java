/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015, 2016 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 */
// Portions Copyright [2017-2019] [Payara Foundation and/or its affiliates]

package org.netbeans.modules.payara.tooling.data;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.modules.payara.tooling.utils.EnumUtils;
import org.openide.util.Parameters;

/**
 * Payara server version.
 * <p/>
 * @author Tomas Kraus, Peter Benedikovic
 * @author Gaurav Gupta
 */
public enum PayaraVersion {

    //add new version
    ////////////////////////////////////////////////////////////////////////////
    // Enum values                                                            //
    ////////////////////////////////////////////////////////////////////////////
    /** GlassFish v3. */
    GF_3        ((short) 3, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, PayaraVersion.GF_3_STR),
    /** GlassFish 3.1. */
    GF_3_1      ((short) 3, (short) 1, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, PayaraVersion.GF_3_1_STR),
    /** Payara 4.1.144 */
    PF_4_1_144 ((short) 4, (short) 1, (short) 0, (short) 0, (short) 14, (short) 4, (short) 0, PayaraVersion.PF_4_1_144_STR),
    /** Payara 4.1.151 */
    PF_4_1_151 ((short) 4, (short) 1, (short) 0, (short) 0, (short) 15, (short) 1, (short) 0, PayaraVersion.PF_4_1_151_STR),
    /** Payara 4.1.152 */
    PF_4_1_152 ((short) 4, (short) 1, (short) 0, (short) 0, (short) 15, (short) 2, (short) 0, PayaraVersion.PF_4_1_152_STR),
    /** Payara 4.1.153 */
    PF_4_1_153 ((short) 4, (short) 1, (short) 0, (short) 0, (short) 15, (short) 3, (short) 0, PayaraVersion.PF_4_1_153_STR),
    /** Payara 4.1.1.154 */
    PF_4_1_1_154 ((short) 4, (short) 1, (short) 1, (short) 0, (short) 15, (short) 4, (short) 0, PayaraVersion.PF_4_1_1_154_STR),
    /** Payara 4.1.1.161 */
    PF_4_1_1_161 ((short) 4, (short) 1, (short) 1, (short) 0, (short) 16, (short) 1, (short) 0, PayaraVersion.PF_4_1_1_161_STR),
    /** Payara 4.1.1.162 */
    PF_4_1_1_162 ((short) 4, (short) 1, (short) 1, (short) 0, (short) 16, (short) 2, (short) 0, PayaraVersion.PF_4_1_1_162_STR),
    /** Payara 4.1.1.163 */
    PF_4_1_1_163 ((short) 4, (short) 1, (short) 1, (short) 0, (short) 16, (short) 3, (short) 0, PayaraVersion.PF_4_1_1_163_STR),
    /** Payara 4.1.1.164 */
    PF_4_1_1_164 ((short) 4, (short) 1, (short) 1, (short) 0, (short) 16, (short) 4, (short) 0, PayaraVersion.PF_4_1_1_164_STR),
    /** Payara 4.1.1.171 */
    PF_4_1_1_171 ((short) 4, (short) 1, (short) 1, (short) 0, (short) 17, (short) 1, (short) 0, PayaraVersion.PF_4_1_1_171_STR),
    /** Payara 4.1.2.172 */
    PF_4_1_2_172 ((short) 4, (short) 1, (short) 2, (short) 0, (short) 17, (short) 2, (short) 0, PayaraVersion.PF_4_1_2_172_STR),
    /** Payara 4.1.2.173 */
    PF_4_1_2_173 ((short) 4, (short) 1, (short) 2, (short) 0, (short) 17, (short) 3, (short) 0, PayaraVersion.PF_4_1_2_173_STR),
    /** Payara 4.1.2.174 */
    PF_4_1_2_174 ((short) 4, (short) 1, (short) 2, (short) 0, (short) 17, (short) 4, (short) 0, PayaraVersion.PF_4_1_2_174_STR),
    /** Payara 4.1.2.181 */
    PF_4_1_2_181 ((short) 4, (short) 1, (short) 2, (short) 0, (short) 18, (short) 1, (short) 0, PayaraVersion.PF_4_1_2_181_STR),
    /** Payara 5.181 */
    PF_5_181 ((short) 5, (short) 0, (short) 0, (short) 0, (short) 18, (short) 1, (short) 0, PayaraVersion.PF_5_181_STR),
    /** Payara 5.182 */
    PF_5_182 ((short) 5, (short) 0, (short) 0, (short) 0, (short) 18, (short) 2, (short) 0, PayaraVersion.PF_5_182_STR),
    /** Payara 5.183 */
    PF_5_183 ((short) 5, (short) 0, (short) 0, (short) 0, (short) 18, (short) 3, (short) 0, PayaraVersion.PF_5_183_STR),
    /** Payara 5.184 */
    PF_5_184 ((short) 5, (short) 0, (short) 0, (short) 0, (short) 18, (short) 4, (short) 0, PayaraVersion.PF_5_184_STR),
    /** Payara 5.191 */
    PF_5_191 ((short) 5, (short) 0, (short) 0, (short) 0, (short) 19, (short) 1, (short) 0, PayaraVersion.PF_5_191_STR);

    //add new version
    /**  A <code>String</code> representation of GF_3 value. */
    static final String GF_3_STR = "3";
    /** Additional <code>String</code> representations of GF_3 value. */
    static final String GF_3_STR_NEXT[] = {"3.0", "3.0.0", "3.0.0.0"};

    /**  A <code>String</code> representation of GF_3_1 value. */
    static final String GF_3_1_STR = "3.1";
    /** Additional <code>String</code> representations of GF_3_1 value. */
    static final String GF_3_1_STR_NEXT[] = {"3.1.0", "3.1.0.0"};
    /**  A <code>String</code> representation of PF_4_1_144 value. */
    static final String PF_4_1_144_STR = "4.1.144";
    /** Additional <code>String</code> representations of 4.1.144 value. */
    static final String PF_4_1_144_STR_NEXT[] = {"4.1.144.0"};
    /**  A <code>String</code> representation of PF_4_1_151 value. */
    static final String PF_4_1_151_STR = "4.1.151";
    /** Additional <code>String</code> representations of 4.1.151 value. */
    static final String PF_4_1_151_STR_NEXT[] = {"4.1.151.0"};
    /**  A <code>String</code> representation of PF_4_1_152 value. */
    static final String PF_4_1_152_STR = "4.1.152";
    /** Additional <code>String</code> representations of 4.1.152 value. */
    static final String PF_4_1_152_STR_NEXT[] = {"4.1.152.0"};
    /**  A <code>String</code> representation of PF_4_1_153 value. */
    static final String PF_4_1_153_STR = "4.1.153";
    /** Additional <code>String</code> representations of 4.1.153 value. */
    static final String PF_4_1_153_STR_NEXT[] = {"4.1.153.0"};
    /**  A <code>String</code> representation of PF_4_1_1_154 value. */
    static final String PF_4_1_1_154_STR = "4.1.1.154";
    /** Additional <code>String</code> representations of 4.1.1.154 value. */
    static final String PF_4_1_1_154_STR_NEXT[] = {"4.1.1.154.0"};
    /**  A <code>String</code> representation of PF_4_1_1_161 value. */
    static final String PF_4_1_1_161_STR = "4.1.1.161";
    /** Additional <code>String</code> representations of 4.1.1.161 value. */
    static final String PF_4_1_1_161_STR_NEXT[] = {"4.1.1.161.0"};
    /**  A <code>String</code> representation of PF_4_1_1_162 value. */
    static final String PF_4_1_1_162_STR = "4.1.1.162";
    /** Additional <code>String</code> representations of 4.1.1.162 value. */
    static final String PF_4_1_1_162_STR_NEXT[] = {"4.1.1.162.0"};
    /**  A <code>String</code> representation of PF_4_1_1_163 value. */
    static final String PF_4_1_1_163_STR = "4.1.1.163";
    /** Additional <code>String</code> representations of 4.1.1.163 value. */
    static final String PF_4_1_1_163_STR_NEXT[] = {"4.1.1.163.0"};
    /**  A <code>String</code> representation of PF_4_1_1_164 value. */
    static final String PF_4_1_1_164_STR = "4.1.1.164";
    /** Additional <code>String</code> representations of 4.1.1.164 value. */
    static final String PF_4_1_1_164_STR_NEXT[] = {"4.1.1.164.0"};
    /**  A <code>String</code> representation of PF_4_1_1_171 value. */
    static final String PF_4_1_1_171_STR = "4.1.1.171";
    /** Additional <code>String</code> representations of 4.1.1.171 value. */
    static final String PF_4_1_1_171_STR_NEXT[] = {"4.1.1.171.0"};
    /**  A <code>String</code> representation of PF_4_1_2_172 value. */
    static final String PF_4_1_2_172_STR = "4.1.2.172";
    /** Additional <code>String</code> representations of 4.1.2.172 value. */
    static final String PF_4_1_2_172_STR_NEXT[] = {"4.1.2.172.0"};
    /**  A <code>String</code> representation of PF_4_1_2_173 value. */
    static final String PF_4_1_2_173_STR = "4.1.2.173";
    /** Additional <code>String</code> representations of 4.1.2.173 value. */
    static final String PF_4_1_2_173_STR_NEXT[] = {"4.1.2.173.0"};
    /**  A <code>String</code> representation of PF_4_1_2_174 value. */
    static final String PF_4_1_2_174_STR = "4.1.2.174";
    /** Additional <code>String</code> representations of 4.1.2.174 value. */
    static final String PF_4_1_2_174_STR_NEXT[] = {"4.1.2.174.0"};
    /**  A <code>String</code> representation of PF_4_1_2_181 value. */
    static final String PF_4_1_2_181_STR = "4.1.2.181";
    /** Additional <code>String</code> representations of 4.1.2.181 value. */
    static final String PF_4_1_2_181_STR_NEXT[] = {"4.1.2.181.0"};
    /**  A <code>String</code> representation of PF_5_181 value. */
    static final String PF_5_181_STR = "5.181";
    /** Additional <code>String</code> representations of 5.181 value. */
    static final String PF_5_181_STR_NEXT[] = {"5.181.0"};
    /**  A <code>String</code> representation of PF_5_182 value. */
    static final String PF_5_182_STR = "5.182";
    /** Additional <code>String</code> representations of 5.182 value. */
    static final String PF_5_182_STR_NEXT[] = {"5.182.0"};
    /**  A <code>String</code> representation of PF_5_183 value. */
    static final String PF_5_183_STR = "5.183";
    /** Additional <code>String</code> representations of 5.183 value. */
    static final String PF_5_183_STR_NEXT[] = {"5.183.0"};
    /**  A <code>String</code> representation of PF_5_184 value. */
    static final String PF_5_184_STR = "5.184";
    /** Additional <code>String</code> representations of 5.184 value. */
    static final String PF_5_184_STR_NEXT[] = {"5.184.0"};
    /**  A <code>String</code> representation of PF_5_184 value. */
    static final String PF_5_191_STR = "5.191";
    /** Additional <code>String</code> representations of 5.184 value. */
    static final String PF_5_191_STR_NEXT[] = {"5.191.0"};
  
    ////////////////////////////////////////////////////////////////////////////
    // Class attributes                                                       //
    ////////////////////////////////////////////////////////////////////////////

    /** Payara version enumeration length. */
    public static final int length = PayaraVersion.values().length;
    
    /** Version elements separator character. */
    public static final char SEPARATOR = '.';

    /** Version elements separator REGEX pattern. */
    public static final String SEPARATOR_PATTERN = "\\.";


    /** 
     * Stored <code>String</code> values for backward <code>String</code>
     * conversion.
     */
    private static final Map<String, PayaraVersion> stringValuesMap
            = new HashMap(2 * values().length);

    // Initialize backward String conversion Map.
    static {
        for (PayaraVersion state : PayaraVersion.values()) {
            stringValuesMap.put(state.toString().toUpperCase(), state);
        }
        //add new version
        initStringValuesMapFromArray(PF_4_1_144, PF_4_1_144_STR_NEXT);
        initStringValuesMapFromArray(PF_4_1_151, PF_4_1_151_STR_NEXT);
        initStringValuesMapFromArray(PF_4_1_152, PF_4_1_152_STR_NEXT);
        initStringValuesMapFromArray(PF_4_1_153, PF_4_1_153_STR_NEXT);
        initStringValuesMapFromArray(PF_4_1_1_154, PF_4_1_1_154_STR_NEXT);
        initStringValuesMapFromArray(PF_4_1_1_161, PF_4_1_1_161_STR_NEXT);
        initStringValuesMapFromArray(PF_4_1_1_162, PF_4_1_1_162_STR_NEXT);
        initStringValuesMapFromArray(PF_4_1_1_163, PF_4_1_1_163_STR_NEXT);
        initStringValuesMapFromArray(PF_4_1_1_164, PF_4_1_1_164_STR_NEXT);
        initStringValuesMapFromArray(PF_4_1_1_171, PF_4_1_1_171_STR_NEXT);
        initStringValuesMapFromArray(PF_4_1_2_172, PF_4_1_2_172_STR_NEXT);
        initStringValuesMapFromArray(PF_4_1_2_173, PF_4_1_2_173_STR_NEXT);
        initStringValuesMapFromArray(PF_4_1_2_174, PF_4_1_2_174_STR_NEXT);
        initStringValuesMapFromArray(PF_4_1_2_181, PF_4_1_2_181_STR_NEXT);
        initStringValuesMapFromArray(PF_5_181, PF_5_181_STR_NEXT);
        initStringValuesMapFromArray(PF_5_182, PF_5_182_STR_NEXT);
        initStringValuesMapFromArray(PF_5_183, PF_5_183_STR_NEXT);
        initStringValuesMapFromArray(PF_5_184, PF_5_184_STR_NEXT);
        initStringValuesMapFromArray(PF_5_191, PF_5_191_STR_NEXT);
    }
    

    ////////////////////////////////////////////////////////////////////////////
    // Static methods                                                         //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Helper method to initialize backward String conversion <code>Map</code> with
     * additional values using additional string values arrays.
     * <p/>
     * @param version Target version for additional values.
     * @param values  Array containing source <code>String</code> values.
     */
    private static void initStringValuesMapFromArray(
            final PayaraVersion version, final String[] values) {
        for (String value : values) {
            stringValuesMap.put(value, version);
        }
    }

    /**
     * Returns a <code>PayaraVersion</code> with a value represented by the
     * specified <code>String</code>. The <code>PayaraVersion</code> returned
     * represents existing value only if specified <code>String</code>
     * matches any <code>String</code> returned by <code>toString</code> method.
     * Otherwise <code>null</code> value is returned.
     * <p/>
     * @param versionStr Value containing version <code>String</code>
     *                   representation.
     * @return <code>PayaraVersion</code> value represented by
     *         <code>String</code> or <code>null</code> if value was
     *         not recognized.
     */
    @CheckForNull
    public static PayaraVersion toValue(@NonNull final String versionStr) {
        Parameters.notNull("versionStr", versionStr);

        PayaraVersion version
                = stringValuesMap.get(versionStr.toUpperCase(Locale.ENGLISH));
        if (version == null) {
            String[] versionNumbers = versionStr.split("\\"+SEPARATOR);
            for (int i = versionNumbers.length - 1;
                    version == null && i > 0; i--) {
                int versionStrLen = i - 1;
                for (int j = 0; j < i; j++) {
                    versionStrLen += versionNumbers[j].length();
                }
                StringBuilder sb = new StringBuilder(versionStrLen);
                for (int j = 0; j < i; j++) {
                    if (j > 0) {
                        sb.append(SEPARATOR);
                    }
                    try {
                        Integer.parseInt(versionNumbers[j]);
                        sb.append(versionNumbers[j]);
                    } catch (NumberFormatException ex) {
                        break;
                    }
                }
                version = stringValuesMap.get(sb.toString().toUpperCase(Locale.ENGLISH));
            }
            if (version == null) {
                // fallback attempt
                int dot = versionStr.indexOf('.');
                if (dot > 0) {
                    try {
                        int major = Integer.parseInt(versionStr.substring(0, dot));
                        // this needs enum to be properly ordered latest versions last
                        for (PayaraVersion v : values()) {
                            if (v.major <= major) {
                                version = v;
                            } else if (v.major > major) {
                                break;
                            }
                        }
                    } catch (NumberFormatException ex) {
                        // noop
                    }
                }
            }
        }
        return version;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Instance attributes                                                    //
    ////////////////////////////////////////////////////////////////////////////

    /** Major version number. */
    private final short major;

    /** Minor version number. */
    private final short minor;

    /** Update version number. */
    private final short update;

    /** Build version number. */
    private final short build;

    private final String value;

    ////////////////////////////////////////////////////////////////////////////
    // Constructors                                                           //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Constructs an instance of Payara server version.
     * <p/>
     * @param major  Major version number.
     * @param minor  Minor version number.
     * @param update Update version number.
     * @param build  Build version number.
     */
    private PayaraVersion(final short major, final short minor,
            final short update, final short build, 
            final short year, final short quarter, 
            final short month, final String value) {
        this.major = major;
        this.minor = minor;
        this.update = update;
        this.build = build;
        this.value = value;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Getters                                                                //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Get major version number.
     * <p/>
     * @return Major version number.
     */
    public short getMajor() {
        return major;
    }

    /**
     * Get minor version number.
     * <p/>
     * @return Minor version number.
     */
    public short getMinor() {
        return minor;
    }

    /**
     * Get update version number.
     * <p/>
     * @return Update version number.
     */
    public short getUpdate() {
        return update;
    }

    /**
     * Get build version number.
     * <p/>
     * @return Build version number.
     */
    public short getBuild() {
        return build;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Methods                                                                //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Compare major and minor parts of version number <code>String</code>s.
     * <p/>
     * @param version Payara server version to compare with this object.
     * @return Value of <code>true</code> when major and minor parts
     *         of version numbers are the same or <code>false</code> otherwise.
     */
    public boolean equalsMajorMinor(final PayaraVersion version) {
        if (version == null) {
            return false;
        } else {
            return this.major == version.major && this.minor == version.minor;
        }
    }

    /**
     * Compare all parts of version number <code>String</code>s.
     * <p/>
     * @param version Payara server version to compare with this object.
     * @return Value of <code>true</code> when all parts of version numbers are
     *         the same or <code>false</code> otherwise.
     */
    @SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
    public boolean equals(final PayaraVersion version) {
        if (version == null) {
            return false;
        } else {
            return this.major == version.major
                    && this.minor == version.minor
                    && this.update == version.update
                    && this.build == version.build;
        }
    }

    /** {@inheritDoc} */
    public static boolean eq(Enum<PayaraVersion> v1, Enum<PayaraVersion> v2) {
        return EnumUtils.eq(v1, v2);
    }

     /** {@inheritDoc} */
    public static boolean ne(Enum<PayaraVersion> v1, Enum<PayaraVersion> v2) {
        return EnumUtils.ne(v1, v2);
    }

     /** {@inheritDoc} */
    public static boolean lt(Enum<PayaraVersion> v1, Enum<PayaraVersion> v2) {
        return EnumUtils.lt(v1, v2);
    }

     /** {@inheritDoc} */
    public static boolean le(Enum<PayaraVersion> v1, Enum<PayaraVersion> v2) {
        return EnumUtils.le(v1, v2);
    }

    /** {@inheritDoc} */
    public static boolean gt(Enum<PayaraVersion> v1, Enum<PayaraVersion> v2) {
        return EnumUtils.gt(v1, v2);
    }

     /** {@inheritDoc} */
    public static boolean ge(Enum<PayaraVersion> v1, Enum<PayaraVersion> v2) {
        return EnumUtils.ge(v1, v2);
    }

    /**
     * Convert <code>PayaraVersion</code> value to <code>String</code>.
     * <p/>
     * @return A <code>String</code> representation of the value of this object.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Convert <code>PayaraVersion</code> value to <code>String</code>
     * containing all version numbers.
     * <p/>
     * @return A <code>String</code> representation of the value of this object
     *         containing all version numbers.
     */
    public String toFullString() {
        StringBuilder sb = new StringBuilder(8);
        sb.append(Integer.toString(major));
        sb.append(SEPARATOR);
        sb.append(Integer.toString(minor));
        sb.append(SEPARATOR);
        sb.append(Integer.toString(update));
        sb.append(SEPARATOR);
        sb.append(Integer.toString(build));
        return sb.toString();
    }

}
