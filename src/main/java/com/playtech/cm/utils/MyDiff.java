package com.playtech.cm.utils;

import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceConstants;
import org.custommonkey.xmlunit.DifferenceListener;
import org.w3c.dom.Node;

import java.util.HashSet;
import java.util.Set;

/**
 * User: Denis Veselovskiy
 * Date: 4/1/13 * Time: 4:32 PM
 */
public class MyDiff implements DifferenceListener {
    private Set<String> blackList = new HashSet<String>();

    public MyDiff(String... elementNames) {
        for (String name : elementNames) {
            blackList.add(name);
        }
    }

    public int differenceFound(Difference difference) {
        if (difference.getId() == DifferenceConstants.TEXT_VALUE_ID) {
            if (blackList.contains(difference.getControlNodeDetail().getNode().getParentNode().getNodeName())) {
                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
            }
        }
        return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
    }

    public void skippedComparison(Node node, Node node1) {

    }
}
