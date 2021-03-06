package hostmock.corba;


/**
* hostmock/corba/HostPOATie.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from idl/Host.idl
* Sunday, December 10, 2017 12:22:07 PM UTC
*/

public class HostPOATie extends HostPOA
{

  // Constructors

  public HostPOATie ( hostmock.corba.HostOperations delegate ) {
      this._impl = delegate;
  }
  public HostPOATie ( hostmock.corba.HostOperations delegate , org.omg.PortableServer.POA poa ) {
      this._impl = delegate;
      this._poa      = poa;
  }
  public hostmock.corba.HostOperations _delegate() {
      return this._impl;
  }
  public void _delegate (hostmock.corba.HostOperations delegate ) {
      this._impl = delegate;
  }
  public org.omg.PortableServer.POA _default_POA() {
      if(_poa != null) {
          return _poa;
      }
      else {
          return super._default_POA();
      }
  }
  public void sndAndRcv (String inq, org.omg.CORBA.StringHolder ans)
  {
    _impl.sndAndRcv(inq, ans);
  } // sndAndRcv

  private hostmock.corba.HostOperations _impl;
  private org.omg.PortableServer.POA _poa;

} // class HostPOATie
