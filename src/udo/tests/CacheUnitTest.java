//@author A0108358B
package udo.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import udo.util.engine.Cache;
import udo.util.exceptions.CacheAccessException;
import udo.util.exceptions.InvalidUIDException;
import udo.util.exceptions.ItemNotFoundException;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;
import udo.util.shared.Constants.Keys;

public class CacheUnitTest {
	// this suite will only test the core crud methods of the cache
	// other aux methods [e.g. getAllPlans()] are not tested
	// this is because their success mostly depends on the core methods.
	// we assume the logic is correct for the aux methods. 

	private static final int PLAN_UID = 12347;
	private static final int NOT_EXIST_UID = 0;
	private static final int INVALID_UID = -1;
	
	// we assume cache.size() works because there is no way 
	// to test it without invoking the other methods under test

	@Test
	public void testCacheAddItem() throws CacheAccessException {
		// puts two items inside the cache 
		// and test whether the items go inside the cache or not
		Cache c = new Cache();
		int oldSize = c.size();
		ItemData i = new ItemData(ItemType.PLAN);
		i.put(Keys.TITLE, "asd");
		ItemData ii = new ItemData(ItemType.PLAN);
		ii.put(Keys.TITLE, "asdasd");
		c.addItem(i);
		c.addItem(ii);
		assertEquals("the new size should be larger than old size by 2",
				c.size(), oldSize + 2);
	}
	
	@SuppressWarnings("null")
	@Test
	public void testCacheGetItem() {
		// puts an item inside the cache 
		// and tries to get it back
		Cache c = new Cache();
		ItemData i = new ItemData(ItemType.PLAN);
		i.put(Keys.TITLE, "asd");
		i.put(Keys.UID, PLAN_UID);
		try {
			c.addItem(i);
		} catch (CacheAccessException e) {
			fail("cache access");
		}
		
		// get the correct item
		ItemData get = null;
		try {
			get = c.getItem(PLAN_UID);
		} catch (CacheAccessException e) {
			fail("cache access");
		} catch (ItemNotFoundException e) {
			fail("item not found");
		} catch (InvalidUIDException e) {
			fail("invalid uid");
		}
		
		assertNotEquals("received item should not be null",
				null,
				get);
		
		assertEquals("received item should have the correct uid",
				PLAN_UID,
				get.get(Keys.UID));
		
		// get the wrong item
		ItemData getWrong = null;
		try {
			getWrong = c.getItem(NOT_EXIST_UID);
		} catch (CacheAccessException e) {
			fail("cache access");
		} catch (ItemNotFoundException e) {
			assertEquals("item not found, item received should still be null",
					null,
					getWrong);
		} catch (InvalidUIDException e) {
			fail("invalid uid");
		}

		// get the invalid item
		ItemData getInvalid = null;
		try {
			getInvalid = c.getItem(INVALID_UID);
		} catch (CacheAccessException e) {
			fail("cache access");
		} catch (ItemNotFoundException e) {
			fail("item not foudn");
		} catch (InvalidUIDException e) {
			assertEquals("invalid uid, item received should still be null",
					null,
					getInvalid);
		}
	}

	@SuppressWarnings("null")
	@Test
	public void testCacheDeleteItem() {
		// puts an item inside the cache 
		// and tries to remove it
		Cache c = new Cache();
		ItemData i = new ItemData(ItemType.PLAN);
		i.put(Keys.TITLE, "asd");
		i.put(Keys.UID, PLAN_UID);
		try {
			c.addItem(i);
		} catch (CacheAccessException e) {
			fail("cache access");
		}
		
		// delete correct item
		ItemData deleted = null;
		try {
			deleted = c.deleteItem(PLAN_UID);
		} catch (CacheAccessException e) {
			fail("cache access");
		} catch (InvalidUIDException e) {
			fail("invalid uid");
		} catch (ItemNotFoundException e) {
			fail("item not found");
		}
		
		assertNotEquals("deleted item not null",
				null,
				deleted);
		
		assertEquals("deleted item should have the correct uid",
				PLAN_UID,
				deleted.get(Keys.UID));

		// delete the wrong item
		ItemData deleteWrong = null;
		try {
			deleteWrong = c.deleteItem(NOT_EXIST_UID);
		} catch (CacheAccessException e) {
			fail("cache access");
		} catch (ItemNotFoundException e) {
			assertEquals("item not found, item deleted should still be null",
					null,
					deleteWrong);
		} catch (InvalidUIDException e) {
			fail("invalid uid");
		}

		// delete the invalid item
		ItemData deleteInvalid = null;
		try {
			deleteInvalid = c.getItem(INVALID_UID);
		} catch (CacheAccessException e) {
			fail("cache access");
		} catch (ItemNotFoundException e) {
			fail("item not found");
		} catch (InvalidUIDException e) {
			assertEquals("invalid uid, item deleted should still be null",
					null,
					deleteInvalid);
		}
	}
	
	@Test
	public void testCacheClear() {
		// adds an item 
		// then clears the cache
		// then checks the cache size
		Cache c = new Cache();
		int emptySize = c.size();
		
		try {
			c.addItem(new ItemData(ItemType.EVENT));
		} catch (CacheAccessException e) {
			fail("cache access");
		}

		int nonEmptySize = c.size();
		
		try {
			c.clear();
		} catch (CacheAccessException e) {
			fail("cache access");
		}
		
		int clearedSize = c.size();
		
		assertNotEquals("cleared size should not be the non empty size",
				nonEmptySize,
				clearedSize);
		
		assertEquals("cleared size should be empty size",
				emptySize,
				clearedSize);
		
	}
}
