package com.emaginalabs.towerlocator.business.service

/**
 * @author Sergio Arroyo - @delr3ves
 */
trait UserLocatorService {

  def trackUser(userId: String, mcc: Integer, net:Integer)

  def findUser(userId:String)
}
