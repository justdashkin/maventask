import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Set;


import static org.junit.Assert.*;

/**
 * Created by Daria_Ivanova2 on 10/24/2016.
 */
public class ArraySetTest {

    @Test
    public void verifySizeEqualsExpectedSize() {
        Set<String> arraySet = new ArraySet<String>();
        arraySet.add("Thorin");
        arraySet.add("Balin");
        arraySet.add("Dwalin");
        arraySet.add("Oin");
        arraySet.add("Gloin");
        Assert.assertEquals(arraySet.size(), 5);
    }

    @Test
    public void verifyIsEmptyReturnsTrueWhenArraySetIsEmpty() {
        ArraySet emptyArray = new ArraySet();
        Assert.assertTrue(emptyArray.isEmpty());
    }

    @Test
    public void verifyIsEmptyReturnsFalseWhenArraySetIsNotEmpty() {
        Set<String> arraySet = new ArraySet<String>();
        arraySet.add("Thorin");
        arraySet.add("Balin");
        arraySet.add("Dwalin");
        arraySet.add("Oin");
        arraySet.add("Gloin");
        Assert.assertFalse(arraySet.isEmpty());
    }

    @Test(expected = ConcurrentModificationException.class)
    public void concurrentModificationExceptionThrowsWhenCollectionIsModifying() {
        ArraySet<String> arraySet = new ArraySet<>();
        arraySet.add("Thorin");
        arraySet.add("Balin");
        arraySet.add("Dwalin");
        arraySet.add("Oin");
        arraySet.add("Gloin");
        for (String srt : arraySet) {
            System.out.println(srt);
            arraySet.add("Bilbo");
        }
    }


    @Test
    public void verifyArrayContainsElementFromArray() {
        List<String> list = new ArrayList<>();
        list.add("Thorin");
        list.add("Balin");
        list.add("Dwalin");
        list.add("Oin");
        list.add("Gloin");
        Set<String> arraySet = new ArraySet<>();
        arraySet.add("Thorin");
        arraySet.add("Balin");
        arraySet.add("Dwalin");
        arraySet.add("Oin");
        arraySet.add("Gloin");
        for (String s : list) {
            Assert.assertTrue(arraySet.contains(s));
        }
    }

    @Test
    public void verifyArrayDoesNotContainElementFromArray() {
        Set<String> arraySet = new ArraySet<>();
        arraySet.add("Thorin");
        arraySet.add("Balin");
        arraySet.add("Dwalin");
        arraySet.add("Oin");
        arraySet.add("Gloin");
        Assert.assertFalse(arraySet.contains("Bilbo"));
    }

    @Test
    public void verifyArrayContainsAddedElement() {
        ArraySet<String> arraySet = new ArraySet<>();
        arraySet.add("Thorin");
        arraySet.add("Balin");
        arraySet.add("Dwalin");
        arraySet.add("Oin");
        arraySet.add("Gloin");
        int sizeBeforeAdding = arraySet.size();
        String addedElement = "Fili";
        arraySet.add(addedElement);
        assertEquals(sizeBeforeAdding + 1, arraySet.size());
        assertEquals(addedElement, arraySet.getElement(arraySet.size() - 1));
    }

    @Test
    public void verifyExistingElementIsNotAdded() {
        List<String> list = new ArrayList<>();
        list.add("Thorin");
        list.add("Balin");
        list.add("Dwalin");
        list.add("Oin");
        list.add("Gloin");
        Set<String> arraySet = new ArraySet<>();
        arraySet.add("Thorin");
        arraySet.add("Balin");
        arraySet.add("Dwalin");
        arraySet.add("Oin");
        arraySet.add("Gloin");
        int sizeBeforeAdding = arraySet.size();
        arraySet.add("Thorin");
        Assert.assertEquals(sizeBeforeAdding, arraySet.size());
        for (String s : list) {
            Assert.assertTrue(arraySet.contains(s));
        }
    }

    @Test
    public void verifyElementIsAddedWhenArraySetIsEmpty() {
        ArraySet emptyArray = new ArraySet();
        String firstElement = "very first element";
        emptyArray.add(firstElement);
        assertEquals(1, emptyArray.size());
    }

    @Test
    public void verifyToArrayReturnsExpectedArray() {
        List<String> list = new ArrayList<>();
        list.add("Thorin");
        list.add("Balin");
        list.add("Dwalin");
        list.add("Oin");
        list.add("Gloin");
        Set<String> arraySet = new ArraySet<>();
        arraySet.add("Thorin");
        arraySet.add("Balin");
        arraySet.add("Dwalin");
        arraySet.add("Oin");
        arraySet.add("Gloin");
        for (String s : list) {
            Assert.assertTrue(arraySet.contains(s));
        }
    }

    @Test
    public void verifyFirstElementIsRemoved() {
        Set<String> arraySet = new ArraySet<>();
        arraySet.add("Thorin");
        arraySet.add("Balin");
        arraySet.add("Dwalin");
        arraySet.add("Oin");
        arraySet.add("Gloin");
        int sizeBeforeRemoving = arraySet.size();
        arraySet.remove("Thorin");
        Assert.assertFalse(arraySet.contains("Thorin"));
        Assert.assertEquals(sizeBeforeRemoving - 1, arraySet.size());
    }

    @Test
    public void verifyLastElementIsRemoved() {
        Set<String> arraySet = new ArraySet<>();
        arraySet.add("Thorin");
        arraySet.add("Balin");
        arraySet.add("Dwalin");
        arraySet.add("Oin");
        arraySet.add("Gloin");
        int sizeBeforeRemoving = arraySet.size();
        arraySet.remove("Gloin");
        Assert.assertFalse(arraySet.contains("Gloin"));
        Assert.assertEquals(sizeBeforeRemoving - 1, arraySet.size());
    }

    @Test
    public void verifyMiddleElementIsRemoved() {
        Set<String> arraySet = new ArraySet<>();
        arraySet.add("Thorin");
        arraySet.add("Balin");
        arraySet.add("Dwalin");
        arraySet.add("Oin");
        arraySet.add("Gloin");
        int sizeBeforeRemoving = arraySet.size();
        arraySet.remove("Dwalin");
        Assert.assertFalse(arraySet.contains("Dwalin"));
        Assert.assertEquals(sizeBeforeRemoving - 1, arraySet.size());
    }

    @Test
    public void verifyArrayWasNotChangedWhenNonexistingElementRemoved() {
        ArraySet<String> arraySet = new ArraySet();
        arraySet.add("Thorin");
        arraySet.add("Balin");
        arraySet.add("Dwalin");
        arraySet.add("Oin");
        arraySet.add("Gloin");
        int sizeBeforeRemoving = arraySet.size();
        arraySet.remove("Bilbo");
        assertEquals(sizeBeforeRemoving, arraySet.size());
    }

    @Test
    public void verifyEmptyArrayWasNotChangedOnRemove() {
        ArraySet emptyArray = new ArraySet();
        emptyArray.remove("nonexisting element!");
        Assert.assertTrue(emptyArray.isEmpty());
    }

    @Test
    public void verifyArrayWithOnlyOneElementBecomesEmptyWhenRemoveThisElement() {
        ArraySet<String> oneElementArray = new ArraySet();
        String oneElement = "the only one";
        oneElementArray.add(oneElement);
        oneElementArray.remove(oneElement);
        Assert.assertTrue(oneElementArray.isEmpty());
    }
}
