<?xml version="1.0"?>

<!--
  ~ The MIT License (MIT)
  ~
  ~ Copyright (c) 2013 Nicolas Moser
  ~
  ~ Permission is hereby granted, free of charge, to any person obtaining a copy of
  ~ this software and associated documentation files (the "Software"), to deal in
  ~ the Software without restriction, including without limitation the rights to
  ~ use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
  ~ the Software, and to permit persons to whom the Software is furnished to do so,
  ~ subject to the following conditions:
  ~
  ~ The above copyright notice and this permission notice shall be included in all
  ~ copies or substantial portions of the Software.
  ~
  ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
  ~ FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
  ~ COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
  ~ IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
  ~ CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
  -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <head>
                <title>Sun Coding Style Violations</title>
            </head>
            <body bgcolor="#FFFFEF">
                <p>
                    <b>Coding Style Check Results</b>
                </p>
                <table border="1" cellspacing="0" cellpadding="2">
                    <tr bgcolor="#CC9966">
                        <th colspan="2">
                            <b>Summary</b>
                        </th>
                    </tr>
                    <tr bgcolor="#CCF3D0">
                        <td>Total files checked</td>
                        <td>
                            <xsl:number level="any" value="count(descendant::file)"/>
                        </td>
                    </tr>
                    <tr bgcolor="#F3F3E1">
                        <td>Files with errors</td>
                        <td>
                            <xsl:number level="any" value="count(descendant::file[error])"/>
                        </td>
                    </tr>
                    <tr bgcolor="#CCF3D0">
                        <td>Total errors</td>
                        <td>
                            <xsl:number level="any" value="count(descendant::error)"/>
                        </td>
                    </tr>
                    <tr bgcolor="#F3F3E1">
                        <td>Errors per file</td>
                        <td>
                            <xsl:number level="any" value="count(descendant::error) div count(descendant::file)"/>
                        </td>
                    </tr>
                </table>
                <hr align="left" width="95%" size="1"/>
                <p>The following are violations of the Sun Coding-Style Standards:</p>
                <p/>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="file[error]">
        <table bgcolor="#AFFFFF" width="95%" border="1" cellspacing="0" cellpadding="2">
            <tr>
                <th>File:</th>
                <td>
                    <xsl:value-of select="@name"/>
                </td>
            </tr>
        </table>
        <table bgcolor="#DFFFFF" width="95%" border="1" cellspacing="0" cellpadding="2">
            <tr>
                <th>Line Number</th>
                <th>Error Message</th>
            </tr>
            <xsl:apply-templates select="error"/>
        </table>
        <p/>
    </xsl:template>

    <xsl:template match="error">
        <tr>
            <td>
                <xsl:value-of select="@line"/>
            </td>
            <td>
                <xsl:value-of select="@message"/>
            </td>
        </tr>
    </xsl:template>

</xsl:stylesheet>
