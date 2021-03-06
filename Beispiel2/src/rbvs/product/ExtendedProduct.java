package rbvs.product;

public class ExtendedProduct extends SimpleProduct {
	private ExtendedProduct savedState = null;

	public ExtendedProduct(ExtendedProduct product) {
		super(product.getName(), product.getPrice());
		if (product.savedState != null) {
			this.savedState = product.savedState.deepCopy();
		} else {
			this.savedState = null;
		}
	}
	
	public ExtendedProduct(String name, float price) {
		super(name, price);
	}
	
	public void setName (String name) {
		this.savedState = new ExtendedProduct(this);
		super.setName(name);
	}
	
	public void setPrice(float price) throws IllegalArgumentException {
		this.savedState = new ExtendedProduct(this);
		try {
			super.setPrice(price);
		} catch (IllegalArgumentException e) {
			throw(e);
		}
	}
	
	public boolean undo () {
		if (this.savedState == null) return false;

		super.setName(this.savedState.getName());
		super.setPrice(this.savedState.getPrice());

		// recursively undo the changes of all saved states
		boolean successful = this.savedState.undo();
		if (!successful) this.savedState = null;
		return true;
	}
	
	@Override
	public String toString() {
		return "ExtendedProduct [ name = " + this.getName() + ", price = " + this.getPrice() + ", undo available = " + (this.savedState != null) + " ]"; 
	}
	
	public ExtendedProduct deepCopy () {
		return new ExtendedProduct(this);
	}

}
