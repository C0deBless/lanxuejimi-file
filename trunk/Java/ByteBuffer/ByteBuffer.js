public abstract class ByteBuffer {

	  // Invariants: mark <= position <= limit <= capacity
    private int mark = -1;
    private int position = 0;
    private int limit;
    private int capacity;

    // Used only by direct buffers
    // NOTE: hoisted here for speed in JNI GetDirectBufferAddress
    long address;

    // Creates a new buffer with the given mark, position, limit, and capacity,
    // after checking invariants.
    //
    Buffer(int mark, int pos, int lim, int cap) {       // package-private
        if (cap < 0)
            throw new IllegalArgumentException("Negative capacity: " + cap);
        this.capacity = cap;
        limit(lim);
        position(pos);
        if (mark >= 0) {
            if (mark > pos)
                throw new IllegalArgumentException("mark > position: ("
                                                   + mark + " > " + pos + ")");
            this.mark = mark;
        }
    }
    
    function capacity() {
        return capacity;
    }

    function position() {
        return position;
    }

    function position(newPosition) {
        if ((newPosition > limit) || (newPosition < 0))
            throw new IllegalArgumentException();
        position = newPosition;
        if (mark > position) mark = -1;
    }

    function limit() {
        return limit;
    }

    public final Buffer limit(int newLimit) {
        if ((newLimit > capacity) || (newLimit < 0))
            throw new IllegalArgumentException();
        limit = newLimit;
        if (position > limit) position = limit;
        if (mark > limit) mark = -1;
        return this;
    }

    /**
	 * Sets this buffer's mark at its position.
	 * </p>
	 * 
	 * @return This buffer
	 */
    public final Buffer mark() {
        mark = position;
        return this;
    }

    public final Buffer reset() {
        int m = mark;
        if (m < 0)
            throw new InvalidMarkException();
        position = m;
        return this;
    }

    public final Buffer clear() {
        position = 0;
        limit = capacity;
        mark = -1;
        return this;
    }

    public final Buffer flip() {
        limit = position;
        position = 0;
        mark = -1;
        return this;
    }

    public final Buffer rewind() {
        position = 0;
        mark = -1;
        return this;
    }

    /**
	 * Returns the number of elements between the current position and the
	 * limit.
	 * </p>
	 * 
	 * @return The number of elements remaining in this buffer
	 */
    public final int remaining() {
        return limit - position;
    }

    /**
	 * Tells whether there are any elements between the current position and the
	 * limit.
	 * </p>
	 * 
	 * @return <tt>true</tt> if, and only if, there is at least one element
	 *         remaining in this buffer
	 */
    public final boolean hasRemaining() {
        return position < limit;
    }

    /**
	 * Tells whether or not this buffer is read-only.
	 * </p>
	 * 
	 * @return <tt>true</tt> if, and only if, this buffer is read-only
	 */
    public abstract boolean isReadOnly();

    /**
	 * Tells whether or not this buffer is backed by an accessible array.
	 * 
	 * <p>
	 * If this method returns <tt>true</tt> then the {@link #array() array}
	 * and {@link #arrayOffset() arrayOffset} methods may safely be invoked.
	 * </p>
	 * 
	 * @return <tt>true</tt> if, and only if, this buffer is backed by an
	 *         array and is not read-only
	 * 
	 * @since 1.6
	 */
    public abstract boolean hasArray();

    /**
	 * Returns the array that backs this buffer&nbsp;&nbsp;<i>(optional
	 * operation)</i>.
	 * 
	 * <p>
	 * This method is intended to allow array-backed buffers to be passed to
	 * native code more efficiently. Concrete subclasses provide more
	 * strongly-typed return values for this method.
	 * 
	 * <p>
	 * Modifications to this buffer's content will cause the returned array's
	 * content to be modified, and vice versa.
	 * 
	 * <p>
	 * Invoke the {@link #hasArray hasArray} method before invoking this method
	 * in order to ensure that this buffer has an accessible backing array.
	 * </p>
	 * 
	 * @return The array that backs this buffer
	 * 
	 * @throws ReadOnlyBufferException
	 *             If this buffer is backed by an array but is read-only
	 * 
	 * @throws UnsupportedOperationException
	 *             If this buffer is not backed by an accessible array
	 * 
	 * @since 1.6
	 */
    public abstract Object array();

    /**
	 * Returns the offset within this buffer's backing array of the first
	 * element of the buffer&nbsp;&nbsp;<i>(optional operation)</i>.
	 * 
	 * <p>
	 * If this buffer is backed by an array then buffer position <i>p</i>
	 * corresponds to array index <i>p</i>&nbsp;+&nbsp;<tt>arrayOffset()</tt>.
	 * 
	 * <p>
	 * Invoke the {@link #hasArray hasArray} method before invoking this method
	 * in order to ensure that this buffer has an accessible backing array.
	 * </p>
	 * 
	 * @return The offset within this buffer's array of the first element of the
	 *         buffer
	 * 
	 * @throws ReadOnlyBufferException
	 *             If this buffer is backed by an array but is read-only
	 * 
	 * @throws UnsupportedOperationException
	 *             If this buffer is not backed by an accessible array
	 * 
	 * @since 1.6
	 */
    public abstract int arrayOffset();

    /**
	 * Tells whether or not this buffer is <a href="ByteBuffer.html#direct"><i>direct</i></a>.
	 * </p>
	 * 
	 * @return <tt>true</tt> if, and only if, this buffer is direct
	 * 
	 * @since 1.6
	 */
    public abstract boolean isDirect();


    // -- Package-private methods for bounds checking, etc. --

    /**
	 * Checks the current position against the limit, throwing a {@link
	 * BufferUnderflowException} if it is not smaller than the limit, and then
	 * increments the position.
	 * </p>
	 * 
	 * @return The current position value, before it is incremented
	 */
    final int nextGetIndex() {                          // package-private
        if (position >= limit)
            throw new BufferUnderflowException();
        return position++;
    }

    final int nextGetIndex(int nb) {                    // package-private
        if (limit - position < nb)
            throw new BufferUnderflowException();
        int p = position;
        position += nb;
        return p;
    }

    /**
	 * Checks the current position against the limit, throwing a {@link
	 * BufferOverflowException} if it is not smaller than the limit, and then
	 * increments the position.
	 * </p>
	 * 
	 * @return The current position value, before it is incremented
	 */
    final int nextPutIndex() {                          // package-private
        if (position >= limit)
            throw new BufferOverflowException();
        return position++;
    }

    final int nextPutIndex(int nb) {                    // package-private
        if (limit - position < nb)
            throw new BufferOverflowException();
        int p = position;
        position += nb;
        return p;
    }

    /**
	 * Checks the given index against the limit, throwing an {@link
	 * IndexOutOfBoundsException} if it is not smaller than the limit or is
	 * smaller than zero.
	 */
    final int checkIndex(int i) {                       // package-private
        if ((i < 0) || (i >= limit))
            throw new IndexOutOfBoundsException();
        return i;
    }

    final int checkIndex(int i, int nb) {               // package-private
        if ((i < 0) || (nb > limit - i))
            throw new IndexOutOfBoundsException();
        return i;
    }

    final int markValue() {                             // package-private
        return mark;
    }

    final void truncate() {                             // package-private
        mark = -1;
        position = 0;
        limit = 0;
        capacity = 0;
    }

    final void discardMark() {                          // package-private
        mark = -1;
    }

    static void checkBounds(int off, int len, int size) { // package-private
        if ((off | len | (off + len) | (size - (off + len))) < 0)
            throw new IndexOutOfBoundsException();
    }
    final byte[] hb;                  // Non-null only for heap buffers
    final int offset;
    boolean isReadOnly;                 // Valid only for heap buffers

    ByteBuffer(int mark, int pos, int lim, int cap,   // package-private
                 byte[] hb, int offset)
    {
        super(mark, pos, lim, cap);
        this.hb = hb;
        this.offset = offset;
    }

    ByteBuffer(int mark, int pos, int lim, int cap) { // package-private
        this(mark, pos, lim, cap, null, 0);
    }



    public static ByteBuffer allocateDirect(int capacity) {
        return new DirectByteBuffer(capacity);
    }



    public static ByteBuffer allocate(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException();
        return new HeapByteBuffer(capacity, capacity);
    }

    public static ByteBuffer wrap(byte[] array,
                                    int offset, int length)
    {
        try {
            return new HeapByteBuffer(array, offset, length);
        } catch (IllegalArgumentException x) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static ByteBuffer wrap(byte[] array) {
        return wrap(array, 0, array.length);
    }

    public abstract ByteBuffer slice();

    public abstract ByteBuffer duplicate();

    public abstract ByteBuffer asReadOnlyBuffer();


    public abstract byte get();

    public abstract byte get(int index);

    public abstract ByteBuffer put(int index, byte b);


    public ByteBuffer get(byte[] dst, int offset, int length) {
        checkBounds(offset, length, dst.length);
        if (length > remaining())
            throw new BufferUnderflowException();
        int end = offset + length;
        for (int i = offset; i < end; i++)
            dst[i] = get();
        return this;
    }

    public ByteBuffer get(byte[] dst) {
        return get(dst, 0, dst.length);
    }


    public ByteBuffer put(ByteBuffer src) {
        if (src == this)
            throw new IllegalArgumentException();
        int n = src.remaining();
        if (n > remaining())
            throw new BufferOverflowException();
        for (int i = 0; i < n; i++)
            put(src.get());
        return this;
    }

    public ByteBuffer put(byte[] src, int offset, int length) {
        checkBounds(offset, length, src.length);
        if (length > remaining())
            throw new BufferOverflowException();
        int end = offset + length;
        for (int i = offset; i < end; i++)
            this.put(src[i]);
        return this;
    }

    public final ByteBuffer put(byte[] src) {
        return put(src, 0, src.length);
    }

    public final boolean hasArray() {
        return (hb != null) && !isReadOnly;
    }

    public final byte[] array() {
        if (hb == null)
            throw new UnsupportedOperationException();
        if (isReadOnly)
            throw new ReadOnlyBufferException();
        return hb;
    }

    public final int arrayOffset() {
        if (hb == null)
            throw new UnsupportedOperationException();
        if (isReadOnly)
            throw new ReadOnlyBufferException();
        return offset;
    }

    public abstract ByteBuffer compact();

    public abstract boolean isDirect();



    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getClass().getName());
        sb.append("[pos=");
        sb.append(position());
        sb.append(" lim=");
        sb.append(limit());
        sb.append(" cap=");
        sb.append(capacity());
        sb.append("]");
        return sb.toString();
    }






    public int hashCode() {
        int h = 1;
        int p = position();
        for (int i = limit() - 1; i >= p; i--)
            h = 31 * h + (int)get(i);
        return h;
    }

    public boolean equals(Object ob) {
        if (this == ob)
            return true;
        if (!(ob instanceof ByteBuffer))
            return false;
        ByteBuffer that = (ByteBuffer)ob;
        if (this.remaining() != that.remaining())
            return false;
        int p = this.position();
        for (int i = this.limit() - 1, j = that.limit() - 1; i >= p; i--, j--)
            if (!equals(this.get(i), that.get(j)))
                return false;
        return true;
    }

    private static boolean equals(byte x, byte y) {



        return x == y;

    }

    public int compareTo(ByteBuffer that) {
        int n = this.position() + Math.min(this.remaining(), that.remaining());
        for (int i = this.position(), j = that.position(); i < n; i++, j++) {
            int cmp = compare(this.get(i), that.get(j));
            if (cmp != 0)
                return cmp;
        }
        return this.remaining() - that.remaining();
    }

    private static int compare(byte x, byte y) {
        return Byte.compare(x, y);
    }


    boolean bigEndian                                   // package-private
        = true;
    boolean nativeByteOrder                             // package-private
        = (Bits.byteOrder() == ByteOrder.BIG_ENDIAN);

    public final ByteOrder order() {
        return bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
    }

    public final ByteBuffer order(ByteOrder bo) {
        bigEndian = (bo == ByteOrder.BIG_ENDIAN);
        nativeByteOrder =
            (bigEndian == (Bits.byteOrder() == ByteOrder.BIG_ENDIAN));
        return this;
    }

    abstract byte _get(int i);                          // package-private
    abstract void _put(int i, byte b);                  // package-private

    public abstract char getChar();

    public abstract ByteBuffer putChar(char value);

    public abstract char getChar(int index);

    public abstract ByteBuffer putChar(int index, char value);

    public abstract CharBuffer asCharBuffer();


    public abstract short getShort();

    public abstract ByteBuffer putShort(short value);

    public abstract short getShort(int index);

    public abstract ByteBuffer putShort(int index, short value);

    public abstract ShortBuffer asShortBuffer();


    public abstract int getInt();

    public abstract ByteBuffer putInt(int value);

    public abstract int getInt(int index);

    public abstract ByteBuffer putInt(int index, int value);

    public abstract IntBuffer asIntBuffer();


    public abstract long getLong();

    public abstract ByteBuffer putLong(long value);

    public abstract long getLong(int index);

    public abstract ByteBuffer putLong(int index, long value);

    public abstract LongBuffer asLongBuffer();


    public abstract float getFloat();

    public abstract ByteBuffer putFloat(float value);

    public abstract float getFloat(int index);

    public abstract ByteBuffer putFloat(int index, float value);

    public abstract FloatBuffer asFloatBuffer();


    public abstract double getDouble();

    public abstract ByteBuffer putDouble(double value);

    public abstract double getDouble(int index);

    public abstract ByteBuffer putDouble(int index, double value);

    public abstract DoubleBuffer asDoubleBuffer();

}