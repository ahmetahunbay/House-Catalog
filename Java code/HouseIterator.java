// --== CS400 Role Code Project Iterator ==--
// Name: Ahmet Ahunbay
// CSL Username: ahunbay
// Email: aaahunbay@wisc.edu
// Lecture #: 003 @2:25pm
// Notes to Grader: N/A
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class HouseIterator implements Iterator<House> {
		Stack<HouseNode> nodeStack = new Stack<HouseNode>();
		private Integer min;
		private Integer max;
		
		public HouseIterator(HouseNode root) {
			tree(root);
		}
		
		public HouseIterator(HouseNode root, Integer min, Integer max) {
			this.min = min;
			this.max = max;
			tree(root);
		}
		
		
		private void tree(HouseNode root) {
			while(min != null && root.data.price < min) {
				if(root.rightChild!= null) {
					root = root.rightChild;
				}else {
					return;
				}
			}
			while(max != null && root.data.price > max) {
				if(root.leftChild != null) {
					root = root.leftChild;
				} else {
					return;
				}
			}
			
			nodeStack.push(root);
			while(root.leftChild!= null) {
				if(min != null && root.leftChild.data.price < min) {
					root = root.leftChild;
					while(root.data.price < min) {
						if(root.rightChild!= null) {
							root = root.rightChild;
						}else {
							return;
						}
					}
					nodeStack.push(root);
					continue;
				}
				root = root.leftChild;
				nodeStack.push(root);
			}
		}
		
		@Override
		public House next() {
			if( !this.hasNext()) return null;
			HouseNode returned = nodeStack.pop();
			if(returned.rightChild != null ) {
				tree(returned.rightChild);
			}
			return returned.data;
		}
	
		@Override
		public boolean hasNext() {
			return !nodeStack.isEmpty();
		}

	}
