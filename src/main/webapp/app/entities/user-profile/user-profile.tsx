import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './user-profile.reducer';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserProfileProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const UserProfile = (props: IUserProfileProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { userProfileList, match, loading } = props;
  return (
    <div>
      <h2 id="user-profile-heading">
        <Translate contentKey="listitApp.userProfile.home.title">User Profiles</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="listitApp.userProfile.home.createLabel">Create new User Profile</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {userProfileList && userProfileList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="listitApp.userProfile.phone">Phone</Translate>
                </th>
                <th>
                  <Translate contentKey="listitApp.userProfile.emailEnabled">Email Enabled</Translate>
                </th>
                <th>
                  <Translate contentKey="listitApp.userProfile.smsEnabled">Sms Enabled</Translate>
                </th>
                <th>
                  <Translate contentKey="listitApp.userProfile.lastLoginDate">Last Login Date</Translate>
                </th>
                <th>
                  <Translate contentKey="listitApp.userProfile.lastLoginIp">Last Login Ip</Translate>
                </th>
                <th>
                  <Translate contentKey="listitApp.userProfile.isLocked">Is Locked</Translate>
                </th>
                <th>
                  <Translate contentKey="listitApp.userProfile.lockDate">Lock Date</Translate>
                </th>
                <th>
                  <Translate contentKey="listitApp.userProfile.maxImages">Max Images</Translate>
                </th>
                <th>
                  <Translate contentKey="listitApp.userProfile.validDays">Valid Days</Translate>
                </th>
                <th>
                  <Translate contentKey="listitApp.userProfile.isPrivacyEnabled">Is Privacy Enabled</Translate>
                </th>
                <th>
                  <Translate contentKey="listitApp.userProfile.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="listitApp.userProfile.location">Location</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userProfileList.map((userProfile, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${userProfile.id}`} color="link" size="sm">
                      {userProfile.id}
                    </Button>
                  </td>
                  <td>{userProfile.phone}</td>
                  <td>{userProfile.emailEnabled ? 'true' : 'false'}</td>
                  <td>{userProfile.smsEnabled ? 'true' : 'false'}</td>
                  <td>
                    {userProfile.lastLoginDate ? (
                      <TextFormat type="date" value={userProfile.lastLoginDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{userProfile.lastLoginIp}</td>
                  <td>{userProfile.isLocked ? 'true' : 'false'}</td>
                  <td>{userProfile.lockDate ? <TextFormat type="date" value={userProfile.lockDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{userProfile.maxImages}</td>
                  <td>{userProfile.validDays}</td>
                  <td>{userProfile.isPrivacyEnabled ? 'true' : 'false'}</td>
                  <td>{userProfile.userId ? userProfile.userId : ''}</td>
                  <td>{userProfile.locationId ? <Link to={`location/${userProfile.locationId}`}>{userProfile.locationId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${userProfile.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${userProfile.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${userProfile.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="listitApp.userProfile.home.notFound">No User Profiles found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ userProfile }: IRootState) => ({
  userProfileList: userProfile.entities,
  loading: userProfile.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserProfile);
