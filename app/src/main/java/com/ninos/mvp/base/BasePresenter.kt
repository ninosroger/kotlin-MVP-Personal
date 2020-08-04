package com.ninos.mvp.base

/**
 * @author Ninos
 *
 * 基础BasePresenter
 * 新版本不在绑定BaseView及其子类，降低耦合
 * 禁止在此类绑定任何跟UI有关事务
 * 可以用此类进行单元测试
 */
abstract class BasePresenter
