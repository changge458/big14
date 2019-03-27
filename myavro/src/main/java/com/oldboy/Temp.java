/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.oldboy;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Temp extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -677532461731100133L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Temp\",\"namespace\":\"com.oldboy\",\"fields\":[{\"name\":\"year\",\"type\":\"string\"},{\"name\":\"temp\",\"type\":\"int\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Temp> ENCODER =
      new BinaryMessageEncoder<Temp>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Temp> DECODER =
      new BinaryMessageDecoder<Temp>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<Temp> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<Temp> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Temp>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this Temp to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a Temp from a ByteBuffer. */
  public static Temp fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public CharSequence year;
  @Deprecated public int temp;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Temp() {}

  /**
   * All-args constructor.
   * @param year The new value for year
   * @param temp The new value for temp
   */
  public Temp(CharSequence year, Integer temp) {
    this.year = year;
    this.temp = temp;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public Object get(int field$) {
    switch (field$) {
    case 0: return year;
    case 1: return temp;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, Object value$) {
    switch (field$) {
    case 0: year = (CharSequence)value$; break;
    case 1: temp = (Integer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'year' field.
   * @return The value of the 'year' field.
   */
  public CharSequence getYear() {
    return year;
  }

  /**
   * Sets the value of the 'year' field.
   * @param value the value to set.
   */
  public void setYear(CharSequence value) {
    this.year = value;
  }

  /**
   * Gets the value of the 'temp' field.
   * @return The value of the 'temp' field.
   */
  public Integer getTemp() {
    return temp;
  }

  /**
   * Sets the value of the 'temp' field.
   * @param value the value to set.
   */
  public void setTemp(Integer value) {
    this.temp = value;
  }

  /**
   * Creates a new Temp RecordBuilder.
   * @return A new Temp RecordBuilder
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Creates a new Temp RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Temp RecordBuilder
   */
  public static Builder newBuilder(Builder other) {
    return new Builder(other);
  }

  /**
   * Creates a new Temp RecordBuilder by copying an existing Temp instance.
   * @param other The existing instance to copy.
   * @return A new Temp RecordBuilder
   */
  public static Builder newBuilder(Temp other) {
    return new Builder(other);
  }

  /**
   * RecordBuilder for Temp instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Temp>
    implements org.apache.avro.data.RecordBuilder<Temp> {

    private CharSequence year;
    private int temp;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.year)) {
        this.year = data().deepCopy(fields()[0].schema(), other.year);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.temp)) {
        this.temp = data().deepCopy(fields()[1].schema(), other.temp);
        fieldSetFlags()[1] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing Temp instance
     * @param other The existing instance to copy.
     */
    private Builder(Temp other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.year)) {
        this.year = data().deepCopy(fields()[0].schema(), other.year);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.temp)) {
        this.temp = data().deepCopy(fields()[1].schema(), other.temp);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'year' field.
      * @return The value.
      */
    public CharSequence getYear() {
      return year;
    }

    /**
      * Sets the value of the 'year' field.
      * @param value The value of 'year'.
      * @return This builder.
      */
    public Builder setYear(CharSequence value) {
      validate(fields()[0], value);
      this.year = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'year' field has been set.
      * @return True if the 'year' field has been set, false otherwise.
      */
    public boolean hasYear() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'year' field.
      * @return This builder.
      */
    public Builder clearYear() {
      year = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'temp' field.
      * @return The value.
      */
    public Integer getTemp() {
      return temp;
    }

    /**
      * Sets the value of the 'temp' field.
      * @param value The value of 'temp'.
      * @return This builder.
      */
    public Builder setTemp(int value) {
      validate(fields()[1], value);
      this.temp = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'temp' field has been set.
      * @return True if the 'temp' field has been set, false otherwise.
      */
    public boolean hasTemp() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'temp' field.
      * @return This builder.
      */
    public Builder clearTemp() {
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Temp build() {
      try {
        Temp record = new Temp();
        record.year = fieldSetFlags()[0] ? this.year : (CharSequence) defaultValue(fields()[0]);
        record.temp = fieldSetFlags()[1] ? this.temp : (Integer) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Temp>
    WRITER$ = (org.apache.avro.io.DatumWriter<Temp>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Temp>
    READER$ = (org.apache.avro.io.DatumReader<Temp>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
