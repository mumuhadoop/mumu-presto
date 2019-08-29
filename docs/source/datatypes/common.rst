presto数据类型
==============

Boolean
>>>>>>>

这种数据类型包含两种值 true或者false

Integer
>>>>>>>

TINYINT
:::::::
八位一字节，数值范围为: -2^7 ~ 2^7 - 1.

SMALLINT
::::::::

2字节16位，数值范围为: -2^15 ~  2^15 - 1

INTEGER
:::::::

4字节32位，数值范围为: -2^31 ~  2^31 - 1

BIGINT
::::::

8字节64位，数值范围为: -2^63 ~ 2^63 - 1

Floating-Point
>>>>>>>>>>>>>>

REAL
::::

A real is a 32-bit inexact, variable-precision implementing the IEEE Standard 754 for Binary Floating-Point Arithmetic.

DOUBLE
::::::

A double is a 64-bit inexact, variable-precision implementing the IEEE Standard 754 for Binary Floating-Point Arithmetic.

Fixed-Precision
>>>>>>>>>>>>>>>

DECIMAL
:::::::

A fixed precision decimal number. Precision up to 38 digits is supported but performance is best up to 18 digits.

The decimal type takes two literal parameters:

- precision - total number of digits
- scale - number of digits in fractional part. Scale is optional and defaults to 0.
Example type definitions: DECIMAL(10,3), DECIMAL(20)

Example literals: DECIMAL '10.3', DECIMAL '1234567890', 1.1

String
>>>>>>

VARCHAR
:::::::

Variable length character data with an optional maximum length.

Example type definitions: varchar, varchar(20)

CHAR
::::

Fixed length character data. A CHAR type without length specified has a default length of 1. A CHAR(x) value always has x characters. For instance, casting dog to CHAR(7) adds 4 implicit trailing spaces. Leading and trailing spaces are included in comparisons of CHAR values. As a result, two character values with different lengths (CHAR(x) and CHAR(y) where x != y) will never be equal.

Example type definitions: char, char(20)

VARBINARY
::::

Variable length binary data.

JSON
::::

JSON value type, which can be a JSON object, a JSON array, a JSON number, a JSON string, true, false or null.

Date and Time
>>>>>>>>>>>>>

DATE
::::

Calendar date (year, month, day).

Example: DATE '2001-08-22'

TIME
::::

Time of day (hour, minute, second, millisecond) without a time zone. Values of this type are parsed and rendered in the session time zone.

Example: TIME '01:02:03.456'

TIME WITH TIME ZONE
:::::::::::::::::::

Time of day (hour, minute, second, millisecond) with a time zone. Values of this type are rendered using the time zone from the value.

Example: TIME '01:02:03.456 America/Los_Angeles'

TIMESTAMP
:::::::::

Instant in time that includes the date and time of day without a time zone. Values of this type are parsed and rendered in the session time zone.

Example: TIMESTAMP '2001-08-22 03:04:05.321'

TIMESTAMP WITH TIME ZONE
::::::::::::::::::::::::

Instant in time that includes the date and time of day with a time zone. Values of this type are rendered using the time zone from the value.

Example: TIMESTAMP '2001-08-22 03:04:05.321 America/Los_Angeles'

INTERVAL YEAR TO MONTH
::::::::::::::::::::::

Span of years and months.

Example: INTERVAL '3' MONTH

INTERVAL DAY TO SECOND
::::::::::::::::::::::

Span of days, hours, minutes, seconds and milliseconds.

Example: INTERVAL '2' DAY

Structural
>>>>>>>>>>

ARRAY
:::::

An array of the given component type.

Example: ARRAY[1, 2, 3]

MAP
:::::

A map between the given component types.

Example: MAP(ARRAY['foo', 'bar'], ARRAY[1, 2])

ROW
::::

A structure made up of named fields. The fields may be of any SQL type, and are accessed with field reference operator .

Example: CAST(ROW(1, 2.0) AS ROW(x BIGINT, y DOUBLE))

Network Address
>>>>>>>>>>>>>>>

IPADDRESS
:::::::::

An IP address that can represent either an IPv4 or IPv6 address. Internally, the type is a pure IPv6 address. Support for IPv4 is handled using the IPv4-mapped IPv6 address range (RFC 4291#section-2.5.5.2). When creating an IPADDRESS, IPv4 addresses will be mapped into that range. When formatting an IPADDRESS, any address within the mapped range will be formatted as an IPv4 address. Other addresses will be formatted as IPv6 using the canonical format defined in RFC 5952.

Examples: IPADDRESS '10.0.0.1', IPADDRESS '2001:db8::1'

HyperLogLog
>>>>>>>>>>>

Calculating the approximate distinct count can be done much more cheaply than an exact count using the HyperLogLog data sketch. See HyperLogLog Functions.

HyperLogLog
:::::::::::

A HyperLogLog sketch allows efficient computation of approx_distinct(). It starts as a sparse representation, switching to a dense representation when it becomes more efficient.


P4HyperLogLog
:::::::::::::

A P4HyperLogLog sketch is similar to HyperLogLog, but it starts (and remains) in the dense representation.

Quantile Digest
>>>>>>>>>>>>>>>

QDigest
:::::::

A quantile digest (qdigest) is a summary structure which captures the approximate distribution of data for a given input set, and can be queried to retrieve approximate quantile values from the distribution. The level of accuracy for a qdigest is tunable, allowing for more precise results at the expense of space.

A qdigest can be used to give approximate answer to queries asking for what value belongs at a certain quantile. A useful property of qdigests is that they are additive, meaning they can be merged together without losing precision.

A qdigest may be helpful whenever the partial results of approx_percentile can be reused. For example, one may be interested in a daily reading of the 99th percentile values that are read over the course of a week. Instead of calculating the past week of data with approx_percentile, qdigests could be stored daily, and quickly merged to retrieve the 99th percentile value.

