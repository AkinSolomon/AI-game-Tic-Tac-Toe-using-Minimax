


public class XYLocation {

		int xCoOrdinate, yCoOrdinate;

		/**
		 * Constructs and initializes a location at the specified (<em>x</em>,
		 * <em>y</em>) location in the coordinate space.
		 * 
		 * @param x
		 *            the x coordinate
		 * @param y
		 *            the y coordinate
		 */
		public XYLocation(int x, int y) {
			this.xCoOrdinate = x;
			this.yCoOrdinate = y;
		}

		/**
		 * Returns the X coordinate of the location in integer precision.
		 * 
		 * @return the X coordinate of the location in double precision.
		 */
		public int getXCoOrdinate() {
			return this.xCoOrdinate;
		}

		public int getYCoOrdinate() {
		   return this.yCoOrdinate;
		}
		
		@Override
		public boolean equals(Object o) {
			if (null == o || !(o instanceof XYLocation)) {
				return super.equals(o);
			}
			XYLocation anotherLoc = (XYLocation) o;
			return ((anotherLoc.getXCoOrdinate() == xCoOrdinate) && (anotherLoc
					.getYCoOrdinate() == yCoOrdinate));
		}
		
		public int getAbsPosition() {
			return this.xCoOrdinate * 3 + this.yCoOrdinate;
		}
		
}
