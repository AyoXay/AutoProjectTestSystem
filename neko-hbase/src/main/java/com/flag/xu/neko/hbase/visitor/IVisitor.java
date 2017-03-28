package com.flag.xu.neko.hbase.visitor;

/**
 * visitor
 *
 * @author xuj
 * @version V1.0-SNAPSHOT
 * @since 2017-03-27-10:43
 */
public interface IVisitor<T> {
    T visit(T t);
}
